package com.viepovsky.clients.car;

import com.viepovsky.clients.car.dto.CarApiDto;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarApiService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarApiService.class);
    private final CarApiClient carApiClient;

    public List<String> getCarModels(int year, String make, String type) {
        List<CarApiDto> carApiDtoList = new ArrayList<>();
        try {
            Thread.sleep(1000);
            carApiDtoList = carApiClient.getCarModels(year, make, type);
            LOGGER.info("Getting car models list with size of: {}", carApiDtoList.size());
        } catch (InterruptedException e) {
            LOGGER.error(
                    "There occurred problem while thread sleeping. Message:{}", e.getMessage());
        }
        return carApiDtoList.stream().map(CarApiDto::model).toList();
    }
}
