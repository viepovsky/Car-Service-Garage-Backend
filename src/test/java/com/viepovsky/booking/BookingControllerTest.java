package com.viepovsky.booking;

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
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@MockBean(ApplicationScheduler.class)
class BookingControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingFacade facade;

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
    void shouldGetEmptyListBookings() throws Exception {
        //Given
        List<BookingDto> bookingDtoList = List.of();
        when(facade.getBookingsByUsername(anyString())).thenReturn(bookingDtoList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/bookings")
                        .param("name", "Testusername")
                        .header("Authorization", "Bearer " + jwtTokenUser))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldGetBookings() throws Exception {
        //Given
        List<BookingDto> bookingDtoList = List.of(new BookingDto(1L, BookingStatus.WAITING_FOR_CUSTOMER.getStatusName(), LocalDate.of(2022, 12, 30), LocalTime.of(10, 0), LocalTime.of(11, 0), BigDecimal.valueOf(50), null, null));
        when(facade.getBookingsByUsername(anyString())).thenReturn(bookingDtoList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/bookings")
                        .param("name", "Testusername")
                        .header("Authorization", "Bearer " + jwtTokenUser))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status", Matchers.is("Waiting for customer")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].date", Matchers.is("2022-12-30")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].startHour", Matchers.is("10:00:00")));
    }

    @Test
    void shouldNotGetBookingsIfGivenUsernameDoesNotMatchUser() throws Exception {
        //Given & when & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/bookings")
                        .param("name", "Testusername22")
                        .header("Authorization", "Bearer " + jwtTokenUser))
                .andExpect(MockMvcResultMatchers.status().isForbidden());
    }

    @Test
    void shouldGetAvailableBookingTimes() throws Exception {
        //Given
        List<LocalTime> localTimeList = List.of(LocalTime.of(10, 0), LocalTime.of(11, 0));
        when(facade.getAvailableBookingTimes(LocalDate.of(2022, 10, 15), 50, 1L, 22L)).thenReturn(localTimeList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/bookings/available-times")
                        .param("date", LocalDate.of(2022, 10, 15).toString())
                        .param("repair-duration", "50")
                        .param("garage-id", "1")
                        .param("car-service-id", "22")
                        .header("Authorization", "Bearer " + jwtTokenUser))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", Matchers.is("10:00:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]", Matchers.is("11:00:00")));
    }

    @Test
    void shouldCreateBooking() throws Exception {
        //Given
        List<Long> idList = List.of(1L, 2L, 5L);
        doNothing().when(facade).createBooking(idList, LocalDate.of(2022, 10, 15), LocalTime.of(10, 0), 33L, 4L, 55);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/bookings")
                        .param("service-id", "1", "2", "5")
                        .param("date", LocalDate.of(2022, 10, 15).toString())
                        .param("start-hour", LocalTime.of(10, 0).toString())
                        .param("garage-id", "33")
                        .param("car-id", "4")
                        .param("repair-duration", "55")
                        .header("Authorization", "Bearer " + jwtTokenUser))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void shouldCreateAvailableBooking() throws Exception {
        //Given
        doNothing().when(facade).createWorkingHoursBooking(any(), any(), any(), anyLong());
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/bookings/admin")
                        .param("date", LocalDate.of(2022, 10, 15).toString())
                        .param("start-hour", LocalTime.of(10, 0).toString())
                        .param("end-hour", LocalTime.of(15, 0).toString())
                        .param("garage-id", "20")
                        .header("Authorization", "Bearer " + jwtTokenAdmin))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    void shouldUpdateBooking() throws Exception {
        //Given
        doNothing().when(facade).updateBooking(1L, LocalDate.of(2022, 10, 15), LocalTime.of(10, 0));
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/bookings/1")
                        .param("date", LocalDate.of(2022, 10, 15).toString())
                        .param("start-hour", LocalTime.of(10, 0).toString())
                        .header("Authorization", "Bearer " + jwtTokenUser))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}