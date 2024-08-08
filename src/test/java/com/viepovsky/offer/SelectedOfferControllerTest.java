package com.viepovsky.offer;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.viepovsky.offer.dto.SelectedOfferDto;
import com.viepovsky.offer.model.RepairStatus;
import com.viepovsky.user.model.AppUser;
import com.viepovsky.user.model.Role;
import com.viepovsky.utility.scheduler.ApplicationScheduler;

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
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@MockBean(ApplicationScheduler.class)
class SelectedOfferControllerTest {
    @Autowired private MockMvc mockMvc;
    @MockBean private SelectedOfferFacade facade;
    @MockBean private UserDetailsService userDetailsService;

    @Value("${jwt.secret.key}")
    private String secretKey;

    private String jwtToken;

    @BeforeEach
    public void initializeUserAndGenerateTokenForUser() {
        var userInDb = AppUser.builder().username("testuser").role(Role.ROLE_USER).build();
        when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userInDb);
        jwtToken = generateToken("testuser", secretKey);
    }

    public static String generateToken(String username, String secretKey) {
        return Jwts.builder()
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
    void testShouldGetEmptySelectedOffers() throws Exception {
        // Given
        List<SelectedOfferDto> emptyList = List.of();
        when(facade.getAllSelectedOffers(anyString())).thenReturn(emptyList);
        // When & then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/v1/selected-offer")
                                .param("username", "testuser")
                                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    void testShouldGetSelectedOffers() throws Exception {
        // Given
        List<SelectedOfferDto> selectedOffers =
                List.of(
                        new SelectedOfferDto(
                                1L,
                                BigDecimal.valueOf(50),
                                BigDecimal.valueOf(1),
                                50,
                                RepairStatus.NOT_ASSIGNED.name(),
                                "details",
                                1L,
                                1L));
        when(facade.getAllSelectedOffers(anyString())).thenReturn(selectedOffers);
        // When & then
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/v1/selected-offer")
                                .param("username", "testuser")
                                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].price", Matchers.is(50)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].discount", Matchers.is(1)));
    }

    @Test
    void testShouldDeleteSelectedOffer() throws Exception {
        // Given
        doNothing().when(facade).deleteSelectedOffer(1L);
        // When & then
        mockMvc.perform(
                        MockMvcRequestBuilders.delete("/v1/selected-offer/1")
                                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
