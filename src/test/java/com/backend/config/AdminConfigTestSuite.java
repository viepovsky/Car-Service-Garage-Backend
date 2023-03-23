package com.backend.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AdminConfigTestSuite {
    @Autowired
    private AdminConfig adminConfig;

    @Test
    void testGetAdminApiKey() {
        //Given
        //When
        String apiKey = adminConfig.getAdminApiKey();
        //Then
        assertNotNull(apiKey);
    }
}