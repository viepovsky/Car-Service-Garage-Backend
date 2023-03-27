package com.backend.controller;

import com.backend.domain.UserRole;
import com.backend.domain.dto.PasswordDto;
import com.backend.domain.dto.UserDto;
import com.backend.facade.UserFacade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
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

import java.io.IOException;
import java.time.LocalDateTime;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(UserController.class)
class UserControllerTestSuite {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserFacade userFacade;

    @Test
    void testGetUser() throws Exception {
        //Given
        UserDto userDto = new UserDto(1L, "Testname", "Testlastname", "test@email", "25325235","Testusername", LocalDateTime.now());
        when(userFacade.getUserByUsername("Testusername")).thenReturn(userDto);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/users/information")
                        .param("username", "Testusername"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber", Matchers.is("25325235")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.is("Testusername")));
    }

    @Test
    void testGetUserToLogin() throws Exception {
        //Given
        UserDto userDto = new UserDto(1L, "Testusername", "Testpassword", UserRole.USER);
        when(userFacade.getUserByUsernameToLogin("Testusername")).thenReturn(userDto);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/users")
                        .param("username", "Testusername"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.is("Testusername")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is("Testpassword")));
    }

    @Test
    void testIsUserRegistered() throws Exception {
        //Given
        when(userFacade.isUserRegistered("username")).thenReturn(true);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/users/is-registered")
                        .param("username", "username"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(true)));
    }

    @Test
    void testGetUserPassword() throws Exception {
        //Given
        PasswordDto passwordDto = new PasswordDto("testpassword");
        when(userFacade.getUserPass("username")).thenReturn(passwordDto);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/v1/users/pass")
                        .param("username", "username"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", Matchers.is("testpassword")));
    }

    @Test
    void testCreateUser() throws Exception {
        //Given
        UserDto userDto = new UserDto(1L,"Testname", "Testlastname", "testmail@mail", "858585858558", "testusername", "testpassword", UserRole.USER, LocalDateTime.now());
        doNothing().when(userFacade).createUser(userDto);

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new TypeAdapter<LocalDateTime>() {
            @Override
            public void write(JsonWriter out, LocalDateTime value) throws IOException {
                out.value(value.toString());
            }

            @Override
            public LocalDateTime read(JsonReader in) throws IOException {
                return LocalDateTime.parse(in.nextString());
            }
        }).create();
        String jsonContent = gson.toJson(userDto);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testUpdateUser() throws Exception {
        //Given
        UserDto userDto = new UserDto(1L,"Testname", "Testlastname", "testmail@mail", "858585858558", "testusername", "testpassword", UserRole.USER, LocalDateTime.now());
        doNothing().when(userFacade).updateUser(userDto);

        Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, new TypeAdapter<LocalDateTime>() {
            @Override
            public void write(JsonWriter out, LocalDateTime value) throws IOException {
                out.value(value.toString());
            }

            @Override
            public LocalDateTime read(JsonReader in) throws IOException {
                return LocalDateTime.parse(in.nextString());
            }
        }).create();
        String jsonContent = gson.toJson(userDto);
        //When & then
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}