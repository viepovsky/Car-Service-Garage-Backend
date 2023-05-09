package com.viepovsky.carrepair;

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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@MockBean(ApplicationScheduler.class)
class CarRepairControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarRepairFacade facade;
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
    void testShouldGetEmptyCarServiceList() throws Exception {
        //Given
        List<CarRepairDto> emptyList = List.of();
        when(facade.getCarServices(anyString())).thenReturn(emptyList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/car-repairs")
                        .param("username", "testuser")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));

    }

    @Test
    void testShouldGetCarServiceList() throws Exception {
        //Given
        List<CarRepairDto> carList = List.of(new CarRepairDto(1L, "Test name", "Test description", BigDecimal.valueOf(50), 60));
        when(facade.getCarServices(anyString())).thenReturn(carList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/car-repairs")
                        .param("username", "testuser")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Test name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cost", Matchers.is(50)));
    }

    @Test
    void testShouldNotGetCarServiceListIfGivenUsernameDoesNotMatchWithUser() throws Exception {
        //Given & When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/car-repairs")
                        .param("username", "testuser22")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void testShouldDeleteCarService() throws Exception {
        //Given
        doNothing().when(facade).deleteCarService(1L);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/car-repairs/1")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}