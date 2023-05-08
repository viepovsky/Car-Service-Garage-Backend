package com.viepovsky.garage;

import com.viepovsky.garage.worktime.GarageWorkTimeDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.viepovsky.garage.worktime.WorkDays;
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

import java.security.Key;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@MockBean(ApplicationScheduler.class)
class GarageControllerTestSuite {
    @Value("${admin.api.key}")
    private String adminApiKey;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GarageFacade garageFacade;
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
    void shouldGetEmptyListAllGarages() throws Exception {
        //Given
        when(garageFacade.getAllGarages()).thenReturn(List.of());
        //When && then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/garages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));

    }
    @Test
    void shouldGetAllGarages() throws Exception {
        //Given
        List<GarageWorkTimeDto> garageWorkTimeDtoList = List.of(new GarageWorkTimeDto(20L, WorkDays.MONDAY, LocalTime.of(10,0), LocalTime.of(11,0)));
        List<GarageDto> garageDtoList = List.of(new GarageDto(1L, "Test garage", "Test address", garageWorkTimeDtoList));
        when(garageFacade.getAllGarages()).thenReturn(garageDtoList);
        //When && then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/garages")
                        .header("Authorization", "Bearer " + jwtToken))
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
    void shouldCreateGarage() throws Exception {
        //Given
        GarageDto garageDto = new GarageDto(1L, "Test garage", "Test address", null);
        when(garageFacade.createGarage(garageDto, adminApiKey)).thenReturn(ResponseEntity.ok().build());

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalTime.class, (JsonDeserializer<LocalTime>) (json, type, jsonDeserializationContext) ->
                ZonedDateTime.parse(json.getAsJsonPrimitive().getAsString()).toLocalTime()).create();
        String jsonContent = gson.toJson(garageDto);

        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", adminApiKey);

        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/garages/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent)
                        .headers(headers)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void shouldDeleteGarage() throws Exception {
        //Given
        when(garageFacade.deleteGarage(1L, adminApiKey)).thenReturn(ResponseEntity.ok().build());

        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", adminApiKey);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/garages/admin/1")
                        .headers(headers)
                        .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}