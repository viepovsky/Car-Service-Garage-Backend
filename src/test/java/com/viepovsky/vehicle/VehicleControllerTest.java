package com.viepovsky.vehicle;

import static com.viepovsky.vehicle.VehicleTestData.TEST_USERNAME;
import static com.viepovsky.vehicle.VehicleTestData.VEHICLE_ENDPOINT_PATH;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viepovsky.user.model.AppUser;
import com.viepovsky.user.model.Role;
import com.viepovsky.utility.scheduler.ApplicationScheduler;
import com.viepovsky.vehicle.dto.VehicleCreateRequest;
import com.viepovsky.vehicle.dto.VehicleDto;
import com.viepovsky.vehicle.dto.VehicleUpdateRequest;

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
class VehicleControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private VehicleFacade facade;
    @MockBean private UserDetailsService userDetailsService;
    private final VehicleTestData testData = new VehicleTestData();

    @Value("${jwt.secret.key}")
    private String secretKey;

    private String jwtToken;

    @BeforeEach
    public void initializeUserAndGenerateTokenForUser() {
        var userInDb = AppUser.builder().username(TEST_USERNAME).role(Role.ROLE_USER).build();
        when(userDetailsService.loadUserByUsername(TEST_USERNAME)).thenReturn(userInDb);
        jwtToken = generateToken(TEST_USERNAME, secretKey);
    }

    public static String generateToken(String username, String secretKey) {
        return Jwts.builder()
                .setClaims(new HashMap<>())
                .setSubject(username)
                .setIssuer("garage-app.com")
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 2))
                .signWith(getSignInKey(secretKey), SignatureAlgorithm.HS256)
                .compact();
    }

    private static Key getSignInKey(String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Test
    void shouldGetVehicle() throws Exception {
        // Given
        VehicleDto response = testData.getVehicleDto();
        String jsonResponse = new ObjectMapper().writeValueAsString(response);

        when(facade.getVehicle(1L)).thenReturn(response);
        // When & then
        mockMvc.perform(
                        MockMvcRequestBuilders.get(VEHICLE_ENDPOINT_PATH + "/1")
                                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse));
    }

    @Test
    void shouldGetEmptyVehicleList() throws Exception {
        // Given
        List<VehicleDto> response = List.of();
        
        when(facade.getVehiclesByUsername(TEST_USERNAME)).thenReturn(response);
        // When & then
        mockMvc.perform(
                        MockMvcRequestBuilders.get(VEHICLE_ENDPOINT_PATH)
                                .param("username", TEST_USERNAME)
                                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldGetVehicleList() throws Exception {
        // Given
        List<VehicleDto> response = List.of(testData.getVehicleDto());
        
        when(facade.getVehiclesByUsername(TEST_USERNAME)).thenReturn(response);
        // When & then
        mockMvc.perform(
                        MockMvcRequestBuilders.get(VEHICLE_ENDPOINT_PATH)
                                .param("username", TEST_USERNAME)
                                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].vehicleId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].engineType", Matchers.is("DIESEL")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].manufactured_year", Matchers.is(2020)));
    }

    @Test
    void shouldCreateVehicle() throws Exception {
        // Given
        VehicleCreateRequest vehicleCreateRequest = testData.getVehicleCreateRequest();
        String jsonRequest = new ObjectMapper().writeValueAsString(vehicleCreateRequest);
        VehicleDto vehicleResponse = testData.getVehicleDto();
        String jsonResponse = new ObjectMapper().writeValueAsString(vehicleResponse);

        when(facade.createVehicle(vehicleCreateRequest, TEST_USERNAME)).thenReturn(vehicleResponse);
        // When & then
        mockMvc.perform(
                        MockMvcRequestBuilders.post(VEHICLE_ENDPOINT_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(jsonRequest)
                                .param("username", TEST_USERNAME)
                                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse));
    }

    @Test
    void shouldUpdateVehicle() throws Exception {
        // Given
        VehicleUpdateRequest vehicleRequest = testData.getVehicleUpdateRequest();
        String jsonRequest = new ObjectMapper().writeValueAsString(vehicleRequest);

        doNothing().when(facade).updateVehicle(any(VehicleUpdateRequest.class));
        // When & then
        mockMvc.perform(
                        MockMvcRequestBuilders.put(VEHICLE_ENDPOINT_PATH)
                                .contentType(MediaType.APPLICATION_JSON)
                                .characterEncoding("UTF-8")
                                .content(jsonRequest)
                                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void shouldDeleteVehicle() throws Exception {
        // Given
        doNothing().when(facade).deleteVehicle(1L);
        // When & then
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/v1/vehicles/1")
                                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
