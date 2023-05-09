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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@MockBean(ApplicationScheduler.class)
class AvailableCarRepairControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AvailableCarRepairFacade facade;
    @MockBean
    private UserDetailsService userDetailsService;

    @Value("${jwt.secret.key}")
    private String secretKey;

    private String jwtTokenUser;
    private String jwtTokenAdmin;

    @BeforeEach
    public void initializeUserAndGenerateTokenForUser() {
        var userInDb = User.builder().username("Testusername").role(Role.ROLE_USER).build();
        var adminInDb = User.builder().username("Testadmin").role(Role.ROLE_ADMIN).build();
        when(userDetailsService.loadUserByUsername("Testusername")).thenReturn(userInDb);
        when(userDetailsService.loadUserByUsername("Testadmin")).thenReturn(adminInDb);
        jwtTokenUser = generateToken("Testusername", secretKey);
        jwtTokenAdmin = generateToken("Testadmin", secretKey);
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
        when(facade.getAvailableCarServices(1L)).thenReturn(List.of());
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/available-car-service/1")
                        .header("Authorization", "Bearer " + jwtTokenUser))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void testShouldGetAllAvailableCarServices() throws Exception {
        //Given
        List<AvailableCarRepairDto> carServiceList = List.of(new AvailableCarRepairDto(1L, "Test service", "Test description", BigDecimal.valueOf(50), 40, "BMW", BigDecimal.valueOf(1.2), 22L));
        when(facade.getAvailableCarServices(1L)).thenReturn(carServiceList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/available-car-service/1")
                        .header("Authorization", "Bearer " + jwtTokenUser))
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
        doNothing().when(facade).createAvailableCarService(any(AvailableCarRepairDto.class), anyLong());
        Gson gson = new Gson();
        String jsonContent = gson.toJson(availableCarRepairDto);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/available-car-service")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent)
                        .param("garage-id", "22")
                        .header("Authorization", "Bearer " + jwtTokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void shouldDeleteAvailableCarService() throws Exception {
        //Given
        doNothing().when(facade).deleteAvailableCarService(anyLong());
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/available-car-service/20")
                        .header("Authorization", "Bearer " + jwtTokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}