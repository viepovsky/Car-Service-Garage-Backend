package com.viepovsky.car;

import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@MockBean(ApplicationScheduler.class)
class CarControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarFacade facade;
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
    void testShouldGetEmptyCarList() throws Exception {
        //Given
        List<CarDto> emptyList = List.of();
        when(facade.getCarsForGivenUsername(anyString())).thenReturn(emptyList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/cars")
                        .param("username", "testuser")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void testShouldGetCarList() throws Exception {
        //Given
        List<CarDto> carList = List.of(new CarDto(1L, "BMW", "3 Series", "Sedan", 2014, "Diesel", 5L));
        when(facade.getCarsForGivenUsername(anyString())).thenReturn(carList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/cars")
                        .param("username", "testuser")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].year", Matchers.is(2014)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].engine", Matchers.is("Diesel")));
    }

    @Test
    void testShouldNotGetCarListIfGivenUsernameDoesNotMatchWithUser() throws Exception {
        //Given & when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/cars")
                        .param("username", "testuser22")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void testShouldCreateCar() throws Exception {
        //Given
        var carRequest = new CarDto(1L, "BMW", "3 Series", "Sedan", 2014, "Diesel", null);
        var jsonRequest = new ObjectMapper().writeValueAsString(carRequest);

        doNothing().when(facade).createCar(any(CarDto.class), anyString());
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonRequest)
                        .param("username", "testuser")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testShouldNotCreateCartIfGivenUsernameDoesNotMatchWithUser() throws Exception {
        var carRequest = new CarDto(1L, "BMW", "3 Series", "Sedan", 2014, "Diesel", null);
        var jsonRequest = new ObjectMapper().writeValueAsString(carRequest);
        //Given & when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonRequest)
                        .param("username", "testuser22")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void testShouldUpdateCar() throws Exception {
        //Given
        var carRequest = new CarDto(1L, "BMW", "3 Series", "Sedan", 2014, "Diesel", null);
        var jsonRequest = new ObjectMapper().writeValueAsString(carRequest);

        doNothing().when(facade).updateCar(any(CarDto.class));
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testShouldDeleteCar() throws Exception {
        //Given
        doNothing().when(facade).deleteCar(1L);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/cars/1")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}