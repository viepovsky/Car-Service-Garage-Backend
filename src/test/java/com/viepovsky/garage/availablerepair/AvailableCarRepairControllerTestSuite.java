package com.viepovsky.garage.availablerepair;

import com.google.gson.Gson;
import com.viepovsky.scheduler.ApplicationScheduler;
import com.viepovsky.user.Role;
import com.viepovsky.user.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@MockBean(ApplicationScheduler.class)
class AvailableCarRepairControllerTestSuite {
    @Value("${admin.api.key}")
    private String adminApiKey;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AvailableCarRepairFacade availableCarRepairFacade;
    @MockBean
    private UserDetailsService userDetailsService;

    @Value("${jwt.secret.key}")
    private String secretKey;

    private String jwtToken;

    @BeforeEach
    public void initializeUserAndGenerateTokenForUser() {
        var userInDb = User.builder().username("testuser").role(Role.ROLE_USER).build();
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userInDb);
        jwtToken = generateToken("testuser", secretKey);
    }

    public static String generateToken(String username, String secretKey) {
        return Jwts
                .builder()
                .setClaims(new HashMap<>())
                .setSubject(username)
                .setIssuer("medical-app.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getSignInKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    private static Key getSignInKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Test
    void testShouldGetEmptyListAvailableCarServices() throws Exception {
        //Given
        when(availableCarRepairFacade.getAvailableCarServices(1L)).thenReturn(List.of());
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/available-car-service/1")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void testShouldGetAllAvailableCarServices() throws Exception {
        //Given
        List<AvailableCarRepairDto> carServiceList = List.of( new AvailableCarRepairDto(1L, "Test service", "Test description", BigDecimal.valueOf(50), 40, "BMW", BigDecimal.valueOf(1.2), 22L));
        when(availableCarRepairFacade.getAvailableCarServices(1L)).thenReturn(carServiceList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/available-car-service/1")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Test service")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cost", Matchers.is(50)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].repairTimeInMinutes", Matchers.is(40)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].makeMultiplier", Matchers.is(1.2)));
    }

    @Test
    void testShouldCreateAvailableCarService() throws Exception {
        //Given
        AvailableCarRepairDto availableCarRepairDto = new AvailableCarRepairDto(1L, "Test service", "Test description", BigDecimal.valueOf(50), 40, "BMW", BigDecimal.valueOf(1.2), null);
        when(availableCarRepairFacade.createAvailableCarService(availableCarRepairDto, 22L, adminApiKey)).thenReturn(ResponseEntity.ok().build());
        Gson gson = new Gson();
        String jsonContent = gson.toJson(availableCarRepairDto);
        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", adminApiKey);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/available-car-service/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent)
                        .headers(headers)
                        .param("garage-id", "22")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldDeleteAvailableCarService() throws Exception {
        //Given
        when(availableCarRepairFacade.deleteAvailableCarService(20L, adminApiKey)).thenReturn(ResponseEntity.ok().build());
        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", adminApiKey);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/available-car-service/admin/20")
                        .headers(headers)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}