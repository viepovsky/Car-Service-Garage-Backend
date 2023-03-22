package com.backend.api.car.service;

import com.backend.api.car.client.CarApiClient;
import com.backend.api.car.domain.CarApiDto;
import com.backend.api.car.domain.StoredCarApi;
import com.backend.api.car.repository.StoredCarApiRepository;
import com.backend.scheduler.TimeKeeper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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

    public void getAndSaveCarYearsMakesTypes(LocalDate localDate) throws InterruptedException {
        Thread.sleep(1000);
        List<Integer> carYearsList = carApiClient.getCarYears();
        LOGGER.info("Received car years list with size of: " + carYearsList.size());
        Thread.sleep(1000);
        List<String> carMakesList = carApiClient.getCarMakes();
        LOGGER.info("Received car makes list with size of: " + carMakesList.size());
        Thread.sleep(1000);
        List<String> carTypesList = carApiClient.getCarTypes();
        LOGGER.info("Received car types list with size of: " + carTypesList.size());

        StoredCarApi storedCarApi = new StoredCarApi(carYearsList, carMakesList, carTypesList, localDate);
        storedCarApiRepository.save(storedCarApi);
        LOGGER.info("Saved CarApiDto.");
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
