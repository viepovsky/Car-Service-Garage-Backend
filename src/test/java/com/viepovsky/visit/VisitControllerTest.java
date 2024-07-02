package com.viepovsky.visit;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.viepovsky.user.model.AppUser;
import com.viepovsky.user.model.Role;
import com.viepovsky.utility.scheduler.ApplicationScheduler;
import com.viepovsky.visit.dto.VisitDto;
import com.viepovsky.visit.dto.VisitFullDetailDto;

import com.viepovsky.visit.model.Visit;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@MockBean(ApplicationScheduler.class)
class VisitControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private VisitFacade facade;
    @MockBean private UserDetailsService userDetailsService;

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
                .setIssuer("garage-app.com")
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
    void shouldGetEmptyVisits() throws Exception {
        //Given
        List<VisitFullDetailDto> visitFullDetailDtos = List.of();
        when(facade.getAllVisits(anyString())).thenReturn(visitFullDetailDtos);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/visits")
                        .param("name", "Testusername")
                        .header("Authorization", "Bearer " + jwtTokenUser))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldGetVisits() throws Exception {
        //Given
        List<VisitFullDetailDto> visitFullDetailDtos = List.of(Mockito.mock(VisitFullDetailDto.class));
        when(facade.getAllVisits(anyString())).thenReturn(visitFullDetailDtos);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/visits")
                        .param("name", "Testusername")
                        .header("Authorization", "Bearer " + jwtTokenUser))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(1)));
    }

    @Test
    void shouldGetAvailableVisitTimes() throws Exception {
        //Given
        List<LocalTime> localTimeList = List.of(LocalTime.of(10, 0), LocalTime.of(11, 0));
        when(facade.getAvailableVisitTimes(LocalDate.of(2022, 10, 15), 50, 1L)).thenReturn(localTimeList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/visits/new-offer-available-times")
                        .param("date", LocalDate.of(2022, 10, 15).toString())
                        .param("repair-duration", "50")
                        .param("garage-id", "1")
                        .header("Authorization", "Bearer " + jwtTokenUser))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", Matchers.is("10:00:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]", Matchers.is("11:00:00")));
    }

    @Test
    void shouldCreateVisit() throws Exception {
        //Given
        List<Long> idList = List.of(1L, 2L, 5L);
        VisitDto visitDto = Mockito.mock(VisitDto.class);
        when(facade.createVisit(idList, LocalDate.of(2022, 10, 15), LocalTime.of(10, 0), 33L, 4L, 55)).thenReturn(visitDto);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/visits")
                        .param("catalog-offer-id", "1", "2", "5")
                        .param("date", LocalDate.of(2022, 10, 15).toString())
                        .param("start-hour", LocalTime.of(10, 0).toString())
                        .param("garage-id", "33")
                        .param("vehicle-id", "4")
                        .param("repair-duration", "55")
                        .header("Authorization", "Bearer " + jwtTokenUser))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void shouldUpdateVisit() throws Exception {
        //Given
        doNothing().when(facade).updateVisit(1L, LocalDate.of(2022, 10, 15), LocalTime.of(10, 0));
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/visits/1")
                        .param("date", LocalDate.of(2022, 10, 15).toString())
                        .param("start-hour", LocalTime.of(10, 0).toString())
                        .header("Authorization", "Bearer " + jwtTokenUser))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}