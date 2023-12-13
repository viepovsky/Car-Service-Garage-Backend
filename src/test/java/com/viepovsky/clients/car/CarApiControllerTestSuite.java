package com.viepovsky.clients.car;

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

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@MockBean(ApplicationScheduler.class)
class CarApiControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarApiService service;

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
    void testGetCarMakes() throws Exception {
        //Given
        List<String> makeList = List.of("AUDI", "BMW", "OPEL", "PEUGEOT");
        when(service.getCarMakes()).thenReturn(makeList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/car-api/makes")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", Matchers.is("AUDI")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2]", Matchers.is("OPEL")));
    }

    @Test
    void testGetCarTypes() throws Exception {
        //Given
        List<String> typeList = List.of("Sedan", "Suv", "Hatchback", "Coupe");
        when(service.getCarTypes()).thenReturn(typeList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/car-api/types")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", Matchers.is("Sedan")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2]", Matchers.is("Hatchback")));
    }

    @Test
    void testGetCarYears() throws Exception {
        //Given
        List<Integer> yearList = List.of(2022, 2021, 2020, 2019);
        when(service.getCarYears()).thenReturn(yearList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/car-api/years")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", Matchers.is(2022)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2]", Matchers.is(2020)));
    }

    @Test
    void testGetCarModels() throws Exception {
        //Given
        List<String> modelList = List.of("A8", "A6", "A5", "A4");
        when(service.getCarModels(2014, "Audi", "Sedan")).thenReturn(modelList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/car-api")
                        .param("year", "2014")
                        .param("make", "Audi")
                        .param("type", "Sedan")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", Matchers.is("A8")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2]", Matchers.is("A5")));
    }

}