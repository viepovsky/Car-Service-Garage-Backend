package com.viepovsky.carservice;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(CarServiceController.class)
class CarServiceControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarServiceFacade carServiceFacade;

    @Test
    void testShouldGetEmptyCarServiceList() throws Exception {
        //Given
        List<CarServiceDto> emptyList = List.of();
        when(carServiceFacade.getCarServices("username")).thenReturn(emptyList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/car-services")
                        .param("username", "username"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));

    }

    @Test
    void testShouldGetCarServiceList() throws Exception {
        //Given
        List<CarServiceDto> carList = List.of(new CarServiceDto(1L, "Test name", "Test description", BigDecimal.valueOf(50), 60));
        when(carServiceFacade.getCarServices("username")).thenReturn(carList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/car-services")
                        .param("username", "username"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Test name")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cost", Matchers.is(50)));
    }

    @Test
    void testShouldDeleteCarService() throws Exception {
        //Given
        doNothing().when(carServiceFacade).deleteCarService(1L);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/car-services/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}