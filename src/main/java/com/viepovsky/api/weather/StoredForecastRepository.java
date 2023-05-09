package com.viepovsky.api.weather;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

interface StoredForecastRepository extends CrudRepository<StoredForecast, Long> {
    StoredForecast findByDateAndCity(LocalDate date, String city);

    List<StoredForecast> findAllByCity(String city);

    void deleteAllByCity(String city);
}
