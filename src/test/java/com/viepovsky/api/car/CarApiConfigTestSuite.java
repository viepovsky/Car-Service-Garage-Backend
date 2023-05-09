package com.viepovsky.api.car;

import com.viepovsky.scheduler.ApplicationScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@MockBean(ApplicationScheduler.class)
class CarApiConfigTestSuite {
    @Autowired
    private CarApiConfig config;

    @Test
    void testCarApiConfig() {
        //Given
        //When
        String endpoint = config.getCarApiEndpoint();
        String key = config.getCarApiKey();
        String host = config.getCarApiHost();
        //Then
        assertNotNull(endpoint);
        assertNotNull(key);
        assertNotNull(host);
    }
}