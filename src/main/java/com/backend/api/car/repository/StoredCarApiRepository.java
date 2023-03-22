package com.backend.api.car.repository;

import com.backend.api.car.domain.StoredCarApiDto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface StoredCarApiRepository extends CrudRepository<StoredCarApiDto, Long> {
    StoredCarApiDto findByDateFetched(LocalDate localDate);
}
