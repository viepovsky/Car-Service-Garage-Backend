package com.backend.api.car.service;

import com.backend.api.car.client.CarApiClient;
import com.backend.api.car.domain.ApiCarDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarApiService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarApiClient.class);
    private final CarApiClient carApiClient;

    public List<String> getCarMakes() throws InterruptedException {
        Thread.sleep(1000);
        return carApiClient.getCarMakes();
    }

    public List<String> getCarTypes() throws InterruptedException {
        Thread.sleep(1000);
        return carApiClient.getCarTypes();
    }

    public List<Integer> getCarYears() throws InterruptedException {
        Thread.sleep(1000);
        return carApiClient.getCarYears();
    }

    public List<String> getCarModels(int year, String make, String type) throws InterruptedException {
        Thread.sleep(1000);
        List<ApiCarDto> apiCarDtoList = carApiClient.getCarModels(year, make, type);
        return apiCarDtoList.stream()
                .map(ApiCarDto::getModel)
                .toList();
    }
}
