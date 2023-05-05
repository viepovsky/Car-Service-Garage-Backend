package com.viepovsky.api.car;

import com.viepovsky.api.car.CarApiController;
import com.viepovsky.api.car.CarApiService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(CarApiController.class)
class CarApiControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarApiService carApiService;

    @Test
    void testGetCarMakes() throws Exception {
        //Given
        List<String> makeList = List.of("AUDI", "BMW", "OPEL", "PEUGEOT");
        when(carApiService.getCarMakes()).thenReturn(makeList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/car-api/makes"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", Matchers.is("AUDI")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2]", Matchers.is("OPEL")));
    }

    @Test
    void testGetCarTypes() throws Exception {
        //Given
        List<String> typeList = List.of("Sedan", "Suv", "Hatchback", "Coupe");
        when(carApiService.getCarTypes()).thenReturn(typeList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/car-api/types"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", Matchers.is("Sedan")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2]", Matchers.is("Hatchback")));
    }

    @Test
    void testGetCarYears() throws Exception {
        //Given
        List<Integer> yearList = List.of(2022, 2021, 2020, 2019);
        when(carApiService.getCarYears()).thenReturn(yearList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/car-api/years"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", Matchers.is(2022)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2]", Matchers.is(2020)));
    }

    @Test
    void testGetCarModels() throws Exception {
        //Given
        List<String> modelList = List.of("A8", "A6", "A5", "A4");
        when(carApiService.getCarModels(2014, "Audi", "Sedan")).thenReturn(modelList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/car-api")
                        .param("year", "2014")
                        .param("make", "Audi")
                        .param("type", "Sedan"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", Matchers.is("A8")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2]", Matchers.is("A5")));
    }

}