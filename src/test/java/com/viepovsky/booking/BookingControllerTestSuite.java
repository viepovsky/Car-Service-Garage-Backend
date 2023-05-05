package com.viepovsky.booking;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(BookingController.class)
class BookingControllerTestSuite {
    @Value("${admin.api.key}")
    private String adminApiKey;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingFacade bookingFacade;

    @Test
    void shouldGetEmptyListBookings() throws Exception {
        //Given
        List<BookingDto> bookingDtoList = List.of();
        when(bookingFacade.getBookingsForGivenUsername("username")).thenReturn(bookingDtoList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/bookings")
                        .param("name", "username"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void shouldGetBookings() throws Exception {
        //Given
        List<BookingDto> bookingDtoList = List.of(new BookingDto(1L, BookingStatus.WAITING_FOR_CUSTOMER.getStatusName(), LocalDate.of(2022,12,30), LocalTime.of(10,0), LocalTime.of(11,0), LocalDateTime.of(LocalDate.of(2022,12,30), LocalTime.of(10,0)), BigDecimal.valueOf(50), null, null));
        when(bookingFacade.getBookingsForGivenUsername("username")).thenReturn(bookingDtoList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/bookings")
                        .param("name", "username"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].status", Matchers.is("Waiting for customer")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].date", Matchers.is("2022-12-30")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].startHour", Matchers.is("10:00:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].created", Matchers.is("2022-12-30T10:00:00")));
    }

    @Test
    void shouldGetAvailableBookingTimes() throws Exception {
        //Given
        List<LocalTime> localTimeList = List.of(LocalTime.of(10,0), LocalTime.of(11, 0));
        when(bookingFacade.getAvailableBookingTimes(LocalDate.of(2022,10,15), 50, 1L, 22L)).thenReturn(localTimeList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/bookings/available-times")
                        .param("date", LocalDate.of(2022,10,15).toString())
                        .param("repair-duration", "50")
                        .param("garage-id", "1")
                        .param("car-service-id", "22"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", Matchers.is("10:00:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1]", Matchers.is("11:00:00")));
    }

    @Test
    void shouldCreateBooking() throws Exception {
        //Given
        List<Long> idList = List.of(1L, 2L, 5L);
        doNothing().when(bookingFacade).createBooking(idList, LocalDate.of(2022,10,15), LocalTime.of(10,0), 33L, 4L, 55);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/bookings")
                        .param("service-id", "1", "2", "5")
                        .param("date", LocalDate.of(2022,10,15).toString())
                        .param("start-hour", LocalTime.of(10,0).toString())
                        .param("garage-id", "33")
                        .param("car-id", "4")
                        .param("repair-duration", "55"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldCreateAvailableBooking() throws Exception {
        //Given
        when(bookingFacade.createAvailableBookingDays(LocalDate.of(2022,10,15), LocalTime.of(10,0), LocalTime.of(15,0), 20L, adminApiKey)).thenReturn(ResponseEntity.ok().build());
        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", adminApiKey);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/bookings/admin")
                        .param("date", LocalDate.of(2022,10,15).toString())
                        .param("start-hour", LocalTime.of(10,0).toString())
                        .param("end-hour", LocalTime.of(15,0).toString())
                        .param("garage-id", "20")
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldUpdateBooking() throws Exception {
        //Given
        doNothing().when(bookingFacade).updateBooking(1L, LocalDate.of(2022,10,15), LocalTime.of(10, 0));
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/bookings/1")
                        .param("date", LocalDate.of(2022,10,15).toString())
                        .param("start-hour", LocalTime.of(10,0).toString()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}