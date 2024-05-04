package com.viepovsky.garage.garage_schedule;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.viepovsky.garage.GarageWorkTimeFacade;
import com.viepovsky.garage.dto.ScheduleDto;
import com.viepovsky.scheduler.ApplicationScheduler;
import com.viepovsky.user.model.Role;
import com.viepovsky.user.model.AppUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
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

import java.io.IOException;
import java.security.Key;
import java.time.LocalTime;
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
class GarageWorkTimeControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GarageWorkTimeFacade facade;
    @MockBean
    private UserDetailsService userDetailsService;

    @Value("${jwt.secret.key}")
    private String secretKey;

    private String jwtTokenUser;
    private String jwtTokenAdmin;

    @BeforeEach
    public void initializeUserAndGenerateTokenForUser() {
        var userInDb = AppUser.builder().username("Testusername").role(Role.ROLE_USER).build();
        var adminInDb = AppUser.builder().username("Testadmin").role(Role.ROLE_ADMIN).build();
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
    void getGarageWorkTimes() throws Exception {
        //Given
        List<ScheduleDto> workTimesResponse = List.of(ScheduleDto.builder().build());
        var jsonResponse = new ObjectMapper().writeValueAsString(workTimesResponse);
        when(facade.getGarageWorkTimes(anyLong())).thenReturn(workTimesResponse);
        //When
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/garage-work-time/1")
                        .header("Authorization", "Bearer " + jwtTokenUser))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(jsonResponse));
    }

    @Test
    void testCreateGarageWorkTime() throws Exception {
        //Given
        ScheduleDto garageWorkTimeDto = new ScheduleDto(1L, WorkDays.MONDAY, LocalTime.of(10, 0), LocalTime.of(15, 0));
        doNothing().when(facade).createGarageWorkTime(any(ScheduleDto.class), anyLong());
        Gson gson = new GsonBuilder().registerTypeAdapter(LocalTime.class, new TypeAdapter<LocalTime>() {
            @Override
            public void write(JsonWriter out, LocalTime value) throws IOException {
                out.value(value.toString());
            }

            @Override
            public LocalTime read(JsonReader in) throws IOException {
                return LocalTime.parse(in.nextString());
            }
        }).create();
        String jsonContent = gson.toJson(garageWorkTimeDto);

        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/garage-work-time/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent)
                        .characterEncoding("UTF-8")
                        .header("Authorization", "Bearer " + jwtTokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void testDeleteGarageWorkTime() throws Exception {
        //Given
        doNothing().when(facade).deleteGarageWorkTime(anyLong());
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/garage-work-time/1")
                        .header("Authorization", "Bearer " + jwtTokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}