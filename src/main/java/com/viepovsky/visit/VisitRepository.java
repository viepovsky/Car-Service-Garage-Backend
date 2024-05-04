package com.viepovsky.visit;

import com.viepovsky.visit.model.Visit;
import com.viepovsky.visit.model.VisitStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
interface VisitRepository extends JpaRepository<Visit, Long> {
    @Query("SELECT v FROM Visit v")
    List<Visit> findBookingsByDateAndStatusAndGarageId(
            LocalDate date, VisitStatus status, Long garageId);

    @Query("SELECT v FROM Visit v")
    List<Visit> findBookingsByDateAndGarageId(LocalDate date, Long garageId);

    //    List<Booking> findBookingsByCarRepairListUserId(Long userId);
    @Query("SELECT v FROM Visit v")
    List<Visit> findBookingsByCarRepairList(Long valueToChangeTODO);

    // TODO FIX THIS

}
