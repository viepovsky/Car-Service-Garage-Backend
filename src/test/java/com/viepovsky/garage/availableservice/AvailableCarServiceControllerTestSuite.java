package com.viepovsky.garage.availableservice;

import com.viepovsky.garage.availableservice.AvailableCarServiceController;
import com.viepovsky.garage.availableservice.AvailableCarServiceDto;
import com.viepovsky.garage.availableservice.AvailableCarServiceFacade;
import com.google.gson.Gson;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(AvailableCarServiceController.class)
class AvailableCarServiceControllerTestSuite {
    @Value("${admin.api.key}")
    private String adminApiKey;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AvailableCarServiceFacade availableCarServiceFacade;

    @Test
    void testShouldGetEmptyListAvailableCarServices() throws Exception {
        //Given
        when(availableCarServiceFacade.getAvailableCarServices(1L)).thenReturn(List.of());
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/available-car-service/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void testShouldGetAllAvailableCarServices() throws Exception {
        //Given
        List<AvailableCarServiceDto> carServiceList = List.of( new AvailableCarServiceDto(1L, "Test service", "Test description", BigDecimal.valueOf(50), 40, "BMW", BigDecimal.valueOf(1.2), 22L));
        when(availableCarServiceFacade.getAvailableCarServices(1L)).thenReturn(carServiceList);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/available-car-service/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name", Matchers.is("Test service")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].cost", Matchers.is(50)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].repairTimeInMinutes", Matchers.is(40)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].makeMultiplier", Matchers.is(1.2)));
    }

    @Test
    void testShouldCreateAvailableCarService() throws Exception {
        //Given
        AvailableCarServiceDto availableCarServiceDto = new AvailableCarServiceDto(1L, "Test service", "Test description", BigDecimal.valueOf(50), 40, "BMW", BigDecimal.valueOf(1.2), null);
        when(availableCarServiceFacade.createAvailableCarService(availableCarServiceDto, 22L, adminApiKey)).thenReturn(ResponseEntity.ok().build());
        Gson gson = new Gson();
        String jsonContent = gson.toJson(availableCarServiceDto);
        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", adminApiKey);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/available-car-service/admin")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent)
                        .headers(headers)
                        .param("garage-id", "22"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldDeleteAvailableCarService() throws Exception {
        //Given
        when(availableCarServiceFacade.deleteAvailableCarService(20L, adminApiKey)).thenReturn(ResponseEntity.ok().build());
        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", adminApiKey);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/available-car-service/admin/20")
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}