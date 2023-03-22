package com.backend.scheduler;

import com.backend.api.car.service.CarApiService;
import com.backend.api.car.domain.StoredCarApiDto;
import com.backend.api.car.repository.StoredCarApiRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ApplicationScheduler {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationScheduler.class);
    private final CarApiService carApiService;
    private final StoredCarApiRepository storedCarApiRepository;

    @Scheduled(fixedRate = 24 * 60 * 60 * 1000, initialDelay = 500)
    public void fetchDataFromCarApi() throws InterruptedException {
        TimeKeeper timeKeeper = TimeKeeper.getInstance();
        timeKeeper.setCurrentDate(LocalDate.now());
        LocalDate localDate = timeKeeper.getCurrentDate();
        LOGGER.info("TimeKeeper set to: " + timeKeeper.getCurrentDate());

        List<Integer>  carYearsList = carApiService.getCarYearsToDb();
        LOGGER.info("Received car years list with size of: " + carYearsList.size());
        List<String>  carMakesList = carApiService.getCarMakesToDb();
        LOGGER.info("Received car makes list with size of: " + carMakesList.size());
        List<String>  carTypesList = carApiService.getCarTypesToDb();
        LOGGER.info("Received car types list with size of: " + carTypesList.size());

        StoredCarApiDto storedCarApiDto = new StoredCarApiDto(carYearsList, carMakesList, carTypesList, localDate);
        storedCarApiRepository.save(storedCarApiDto);
        LOGGER.info("Saved CarApiDto.");
    }
}
