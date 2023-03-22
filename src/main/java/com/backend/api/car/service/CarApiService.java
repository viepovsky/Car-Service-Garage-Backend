package com.backend.api.car.service;

import com.backend.api.car.client.CarApiClient;
import com.backend.api.car.domain.CarApiDto;
import com.backend.api.car.repository.StoredCarApiRepository;
import com.backend.scheduler.TimeKeeper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarApiService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarApiService.class);
    private final CarApiClient carApiClient;
    private final StoredCarApiRepository storedCarApiRepository;
    private TimeKeeper timeKeeper = TimeKeeper.getInstance();

    public List<String> getCarMakes() {
        LOGGER.info("Getting car makes with TimeKeeper date: " + timeKeeper.getCurrentDate());
        return storedCarApiRepository.findByDateFetched(timeKeeper.getCurrentDate()).getCarMakesList();
    }

    public List<String> getCarTypes() {
        LOGGER.info("Getting car types with TimeKeeper date: " + timeKeeper.getCurrentDate());
        return storedCarApiRepository.findByDateFetched(timeKeeper.getCurrentDate()).getCarTypesList();
    }

    public List<Integer> getCarYears() {
        LOGGER.info("Getting car years with TimeKeeper date: " + timeKeeper.getCurrentDate());
        return storedCarApiRepository.findByDateFetched(timeKeeper.getCurrentDate()).getCarYearsList();
    }

    public List<String> getCarMakesToDb() throws InterruptedException {
        Thread.sleep(1000);
        return carApiClient.getCarMakes();
    }

    public List<String> getCarTypesToDb() throws InterruptedException {
        Thread.sleep(1000);
        return carApiClient.getCarTypes();
    }

    public List<Integer> getCarYearsToDb() throws InterruptedException {
        Thread.sleep(1000);
        return carApiClient.getCarYears();
    }

    public List<String> getCarModels(int year, String make, String type) throws InterruptedException {
        Thread.sleep(1000);
        List<CarApiDto> carApiDtoList = carApiClient.getCarModels(year, make, type);
        LOGGER.info("Getting car models list with size of: " + carApiDtoList.size());
        return carApiDtoList.stream()
                .map(CarApiDto::getModel)
                .toList();
    }
}
