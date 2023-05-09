package com.viepovsky.api.weather;

import com.viepovsky.api.weather.dto.CityForecastDto;
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
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@MockBean(ApplicationScheduler.class)
class WeatherApiControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherApiService service;
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
    void testGetForecastForGivenCityAndDate() throws Exception {
        //Given
        CityForecastDto cityForecastDto = new CityForecastDto(LocalDate.of(2022, 10, 15), "R20", "Raining", 8, 2, 30, "Poznan");
        when(service.getForecastForCityAndDate("Poznan", LocalDate.of(2022, 10, 15))).thenReturn(cityForecastDto);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/weather-api")
                        .param("city", "Poznan")
                        .param("date", LocalDate.of(2022, 10, 15).toString())
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.symbol", Matchers.is("R20")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.symbolPhrase", Matchers.is("Raining")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.maxTemp", Matchers.is(8)));
    }
}