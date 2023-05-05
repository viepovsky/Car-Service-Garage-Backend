package com.viepovsky.car;

import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(CarController.class)
class CarControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarFacade carFacade;

    @Test
    void testShouldGetEmptyCarList() throws Exception {
        //Given
        List<CarDto> emptyList = List.of();
        when(carFacade.getCarsForGivenUsername("username")).thenReturn(emptyList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/cars")
                .param("username", "username"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void testShouldGetCarList() throws Exception {
        //Given
        List<CarDto> carList = List.of(new CarDto(1L, "BMW", "3 Series", "Sedan", 2014, "Diesel", 5L));
        when(carFacade.getCarsForGivenUsername("username")).thenReturn(carList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/cars")
                        .param("username", "username"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].year", Matchers.is(2014)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].engine", Matchers.is("Diesel")));
    }

    @Test
    void testShouldCreateCar() throws Exception {
        //Given
        CarDto carDto = new CarDto(1L, "BMW", "3 Series", "Sedan", 2014, "Diesel", null);
        doNothing().when(carFacade).createCar(carDto, "username");
        Gson gson = new Gson();
        String jsonContent = gson.toJson(carDto);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent)
                        .param("username", "username"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testShouldUpdateCar() throws Exception {
        //Given
        CarDto carDto = new CarDto(1L, "BMW", "3 Series", "Sedan", 2014, "Diesel", null);
        doNothing().when(carFacade).updateCar(carDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(carDto);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testShouldDeleteCar() throws Exception {
        //Given
        doNothing().when(carFacade).deleteCar(1L);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/cars/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}