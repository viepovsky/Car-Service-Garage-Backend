package com.viepovsky.garage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.viepovsky.garage.garage_work_time.GarageWorkTimeDto;
import com.viepovsky.garage.garage_work_time.WorkDays;
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
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
class GarageControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GarageFacade facade;
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
    void shouldGetEmptyListAllGarages() throws Exception {
        //Given
        when(facade.getAllGarages()).thenReturn(List.of());
        //When && then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/garages")
                        .header("Authorization", "Bearer " + jwtTokenUser))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));

    }

    @Test
    void shouldGetAllGarages() throws Exception {
        //Given
        List<GarageWorkTimeDto> garageWorkTimeDtoList = List.of(new GarageWorkTimeDto(20L, WorkDays.MONDAY, LocalTime.of(10, 0), LocalTime.of(11, 0)));
        List<GarageDto> garageDtoList = List.of(new GarageDto(1L, "Test garage", "Test address", garageWorkTimeDtoList));
        when(facade.getAllGarages()).thenReturn(garageDtoList);
        //When && then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/garages")
                        .header("Authorization", "Bearer " + jwtTokenUser))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Test garage")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].address", Matchers.is("Test address")))

                .andExpect(MockMvcResultMatchers.jsonPath("$[0].garageWorkTimeDtoList[0].id", Matchers.is(20)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].garageWorkTimeDtoList[0].day", Matchers.is("MONDAY")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].garageWorkTimeDtoList[0].startHour", Matchers.is(LocalTime.of(10, 0).format(DateTimeFormatter.ofPattern("HH:mm:ss")))))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].garageWorkTimeDtoList[0].endHour", Matchers.is(LocalTime.of(11, 0).format(DateTimeFormatter.ofPattern("HH:mm:ss")))));
    }

    @Test
    void shouldGetGarage() throws Exception {
        //Given
        var garageResponse = new GarageDto(1L, "Test garage", "Test address", null);
        var jsonResponse = new ObjectMapper().writeValueAsString(garageResponse);
        when(facade.getGarage(anyLong())).thenReturn(garageResponse);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/garages/1")
                        .header("Authorization", "Bearer " + jwtTokenUser))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse));
    }

    @Test
    void shouldCreateGarage() throws Exception {
        //Given
        var garageRequest = GarageDto.builder().name("Test garage").address("Test address").build();
        var garageResponse = Garage.builder().id(1L).build();

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalTime.class, (JsonDeserializer<LocalTime>) (json, type, jsonDeserializationContext) ->
                ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalTime()).create();
        var jsonRequest = gson.toJson(garageRequest);

        when(facade.createGarage(any(GarageDto.class))).thenReturn(garageResponse);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/garages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonRequest)
                        .header("Authorization", "Bearer " + jwtTokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().string("Location", "/v1/garages/" + garageResponse.getId()));

    }

    @Test
    void shouldDeleteGarage() throws Exception {
        //Given
        doNothing().when(facade).deleteGarage(anyLong());

        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/garages/1")
                        .header("Authorization", "Bearer " + jwtTokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}