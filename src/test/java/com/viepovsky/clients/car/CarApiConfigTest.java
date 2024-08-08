package com.viepovsky.clients.car;

import com.viepovsky.scheduler.ApplicationScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@MockBean(ApplicationScheduler.class)
class CarApiConfigTest {
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