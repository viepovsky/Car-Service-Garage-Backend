package com.viepovsky.clients.car;

import com.viepovsky.scheduler.TimeKeeper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CarApiService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CarApiService.class);
    private final CarApiClient client;
    private final StoredCarApiRepository repository;
    private TimeKeeper timeKeeper = TimeKeeper.getInstance();

    public List<String> getCarMakes() {
        LOGGER.info("Getting car makes with TimeKeeper date: " + timeKeeper.getCurrentDate());
        return repository.findByDateFetched(timeKeeper.getCurrentDate()).getCarMakesList();
    }

    public List<String> getCarTypes() {
        LOGGER.info("Getting car types with TimeKeeper date: " + timeKeeper.getCurrentDate());
        return repository.findByDateFetched(timeKeeper.getCurrentDate()).getCarTypesList();
    }

    public List<Integer> getCarYears() {
        LOGGER.info("Getting car years with TimeKeeper date: " + timeKeeper.getCurrentDate());
        return repository.findByDateFetched(timeKeeper.getCurrentDate()).getCarYearsList();
    }

    public void getAndSaveCarYearsMakesTypes(LocalDate localDate) throws InterruptedException {
        Thread.sleep(1000);
        List<Integer> carYearsList = client.getCarYears();
        LOGGER.info("Received car years list with size of: " + carYearsList.size());
        Thread.sleep(1000);
        List<String> carMakesList = client.getCarMakes();
        LOGGER.info("Received car makes list with size of: " + carMakesList.size());
        Thread.sleep(1000);
        List<String> carTypesList = client.getCarTypes();
        LOGGER.info("Received car types list with size of: " + carTypesList.size());

        StoredCarApi storedCarApi = new StoredCarApi(carYearsList, carMakesList, carTypesList, localDate);
        repository.save(storedCarApi);
        LOGGER.info("Saved CarApiDto.");
    }

    public List<String> getCarModels(int year, String make, String type) throws InterruptedException {
        Thread.sleep(1000);
        List<CarApiDto> carApiDtoList = client.getCarModels(year, make, type);
        LOGGER.info("Getting car models list with size of: " + carApiDtoList.size());
        return carApiDtoList.stream()
                .map(CarApiDto::getModel)
                .toList();
    }
}
