package com.viepovsky.garage.worktime;

import com.viepovsky.garage.worktime.GarageWorkTimeController;
import com.viepovsky.garage.worktime.GarageWorkTimeDto;
import com.viepovsky.garage.worktime.GarageWorkTimeFacade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
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

import java.io.IOException;
import java.time.LocalTime;

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(GarageWorkTimeController.class)
class GarageWorkTimeControllerTestSuite {
    @Value("${admin.api.key}")
    private String adminApiKey;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GarageWorkTimeFacade garageWorkTimeFacade;

    @Test
    void testCreateGarageWorkTime() throws Exception {
        //Given
        GarageWorkTimeDto garageWorkTimeDto = new GarageWorkTimeDto(1L, "MONDAY", LocalTime.of(10,0), LocalTime.of(15,0));
        when(garageWorkTimeFacade.createGarageWorkTime(garageWorkTimeDto, 1L, adminApiKey)).thenReturn(ResponseEntity.ok().build());

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

        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", adminApiKey);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/garage-work-time/admin/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent)
                        .characterEncoding("UTF-8")
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testDeleteGarageWorkTime() throws Exception {
        //Given
        when(garageWorkTimeFacade.deleteGarageWorkTime(1L, adminApiKey)).thenReturn(ResponseEntity.ok().build());
        HttpHeaders headers = new HttpHeaders();
        headers.set("api-key", adminApiKey);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/v1/garage-work-time/admin/1")
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}