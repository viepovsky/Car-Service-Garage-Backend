package com.backend.api.weather.repository;

import com.backend.api.weather.domain.StoredForecast;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;

public interface StoredForecastRepository extends CrudRepository<StoredForecast, Long> {
    StoredForecast findByDateAndCity(LocalDate date, String city);
    List<StoredForecast> findAllByCity(String city);
    void deleteAllByCity(String city);
}
