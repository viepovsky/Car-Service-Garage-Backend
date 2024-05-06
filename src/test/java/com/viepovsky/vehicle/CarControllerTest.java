package com.viepovsky.vehicle;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viepovsky.user.model.AppUser;
import com.viepovsky.user.model.Role;
import com.viepovsky.utility.mapper.VehicleMapper;
import com.viepovsky.utility.scheduler.ApplicationScheduler;
import com.viepovsky.vehicle.dto.VehicleCreateRequest;
import com.viepovsky.vehicle.dto.VehicleDto;
import com.viepovsky.vehicle.dto.VehicleUpdateRequest;
import com.viepovsky.vehicle.model.EngineType;

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

@SpringBootTest
@AutoConfigureMockMvc
@MockBean(ApplicationScheduler.class)
class CarControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private VehicleFacade facade;
    @MockBean private VehicleMapper vehicleMapper;
    @MockBean private UserDetailsService userDetailsService;

    @Value("${jwt.secret.key}")
    private String secretKey;

    private String jwtToken;

    @BeforeEach
    public void initializeUserAndGenerateTokenForUser() {
        var userInDb = AppUser.builder().username("testuser").role(Role.ROLE_USER).build();
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
        List<VehicleDto> emptyList = List.of();
        when(facade.getVehiclesByUsername(anyString())).thenReturn(emptyList);
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
        List<VehicleDto> carList = List.of();//TODO fix it List.of(new VehicleDto(1L, "BMW", "3 Series", "Sedan", 2014, "Diesel", 5L));
        when(facade.getVehiclesByUsername(anyString())).thenReturn(carList);
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
        // Given
        var vehicleCreateRequest =
                VehicleCreateRequest.builder()
                        .vin("VIN")
                        .licensePlate("LICENSE_PLATE")
                        .modelId(1L)
                        .engineType(EngineType.DIESEL.name())
                        .manufactured_year(2020)
                        .build();
        var jsonRequest = new ObjectMapper().writeValueAsString(vehicleCreateRequest);
        var vehicle = vehicleMapper.toVehicle(vehicleCreateRequest);
        var vehicleResponse = vehicleMapper.toVehicleDto(vehicle);
        when(facade.createVehicle(vehicleCreateRequest, "testuser")).thenReturn(vehicleResponse);
        // When & then
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/vehicles")
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(jsonRequest)
                                .param("username", "testuser")
                                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testShouldNotCreateCartIfGivenUsernameDoesNotMatchWithUser() throws Exception {
        var carRequest = List.of();//TODO fix it new VehicleDto(1L, "BMW", "3 Series", "Sedan", 2014, "Diesel", null);
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
        var carRequest = List.of();//TODO fix it new VehicleDto(1L, "BMW", "3 Series", "Sedan", 2014, "Diesel", null);
        var jsonRequest = new ObjectMapper().writeValueAsString(carRequest);

        doNothing().when(facade).updateVehicle(any(VehicleUpdateRequest.class));
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
        doNothing().when(facade).deleteVehicle(1L);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/cars/1")
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}