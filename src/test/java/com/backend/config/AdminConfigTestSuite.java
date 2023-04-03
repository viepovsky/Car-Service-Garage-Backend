package com.backend.config;

import com.backend.scheduler.ApplicationScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@MockBean(ApplicationScheduler.class)
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