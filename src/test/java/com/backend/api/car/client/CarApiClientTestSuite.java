package com.backend.api.car.client;

import com.backend.api.car.domain.ApiCarDto;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class CarApiClientTestSuite {

    private static final Logger LOGGER = LoggerFactory.getLogger(CarApiClientTestSuite.class);

    @Autowired
    RestTemplate restTemplate;
    @Autowired
    CarApiClient carApiClient;

//    @Test
//    public void testGetCarMakes() throws InterruptedException {
//        Thread.sleep(1100); // free Api version supports only one request per second
//        List<String> carMakes = carApiClient.getCarMakes();
//        LOGGER.info("Car makes list size: " + carMakes.size());
//        assertTrue(carMakes.size()!=0);
//        assertTrue(carMakes.contains("BMW"));
//    }

//    @Test
//    public void testGetCarYears() throws InterruptedException {
//        Thread.sleep(1100); // free Api version supports only one request per second
//        List<Integer> carYears = carApiClient.getCarYears();
//        LOGGER.info("Car years list size: " + carYears.size());
//        assertTrue(carYears.size()!=0);
//        assertTrue(carYears.contains(2020));
//    }

//    @Test
//    public void testGetCarTypes() throws InterruptedException {
//        Thread.sleep(1100); // free Api version supports only one request per second
//        List<String> carTypes = carApiClient.getCarTypes();
//        LOGGER.info("Car types list size: " + carTypes.size());
//        assertTrue(carTypes.size()!=0);
//        assertTrue(carTypes.contains("Sedan"));
//    }

//    @Test
//    public void testGetCarModels() throws InterruptedException {
//        Thread.sleep(1100); // free Api version supports only one request per second
//        List<ApiCarDto> carModels = carApiClient.getCarModels(2020, "BMW", "Sedan");
//        LOGGER.info("Car models list size: " + carModels.size());
//        try {
//            LOGGER.info("Received first model: " + carModels.get(0).getModel());
//        } catch (IndexOutOfBoundsException e) {
//            LOGGER.info("List is empty. " + e.getMessage());
//        }
//        assertTrue(carModels.size()!=0);
//        assertNotNull(carModels.get(0).getModel());
//    }
}