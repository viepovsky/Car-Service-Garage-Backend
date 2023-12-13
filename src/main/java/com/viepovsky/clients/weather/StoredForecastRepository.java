package com.viepovsky.clients.weather;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

interface StoredForecastRepository extends JpaRepository<StoredForecast, Long> {
    StoredForecast findByDateAndCity(LocalDate date, String city);

    List<StoredForecast> findAllByCity(String city);

    void deleteAllByCity(String city);
}
