package com.backend.api.weather.controller;

import com.backend.api.weather.domain.CityForecastDto;
import com.backend.api.weather.service.WeatherApiService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(WeatherApiController.class)
class WeatherApiControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherApiService weatherApiService;

    @Test
    void testGetForecastForGivenCityAndDate() throws Exception {
        //Given
        CityForecastDto cityForecastDto = new CityForecastDto(LocalDate.of(2022, 10, 15), "R20", "Raining", 8, 2, 30, "Poznan");
        when(weatherApiService.getForecastForCityAndDate("Poznan", LocalDate.of(2022, 10, 15))).thenReturn(cityForecastDto);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/weather-api")
                .param("city", "Poznan")
                .param("date", LocalDate.of(2022,10,15).toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.symbol", Matchers.is("R20")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.symbolPhrase", Matchers.is("Raining")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.maxTemp", Matchers.is(8)));
    }
}