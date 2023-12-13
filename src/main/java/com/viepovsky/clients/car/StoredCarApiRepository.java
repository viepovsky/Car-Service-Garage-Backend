package com.viepovsky.clients.car;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
interface StoredCarApiRepository extends CrudRepository<StoredCarApi, Long> {
    StoredCarApi findByDateFetched(LocalDate localDate);
}
