package com.viepovsky.clients.car;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
interface StoredCarApiRepository extends JpaRepository<StoredCarApi, Long> {
    StoredCarApi findByDateFetched(LocalDate localDate);
}
