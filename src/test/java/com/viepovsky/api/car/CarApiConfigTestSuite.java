package com.viepovsky.api.car;

import com.viepovsky.api.car.CarApiConfig;
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
    private CarApiConfig carApiConfig;

    @Test
    void testCarApiConfig() {
        //Given
        //When
        String endpoint = carApiConfig.getCarApiEndpoint();
        String key = carApiConfig.getCarApiKey();
        String host = carApiConfig.getCarApiHost();
        //Then
        assertNotNull(endpoint);
        assertNotNull(key);
        assertNotNull(host);
    }
}