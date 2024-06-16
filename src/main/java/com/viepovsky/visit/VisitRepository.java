package com.viepovsky.visit;

import com.viepovsky.visit.model.Visit;
import com.viepovsky.visit.model.VisitStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
interface VisitRepository extends JpaRepository<Visit, Long> {
    @Query("SELECT v FROM Visit v")
    List<Visit> findBookingsByDateAndStatusAndGarageId(
            LocalDate date, VisitStatus status, Long garageId);

    @Query(
            "SELECT v FROM Visit v WHERE v.garage.id = :garageId AND :date BETWEEN v.visitStartDate AND v.visitEndDate")
    List<Visit> getVisitsForGarageAndDate(
            @Param("garageId") Long garageId, @Param("date") LocalDate date);

    //    List<Booking> findBookingsByCarRepairListUserId(Long userId);
    @Query("SELECT v FROM Visit v WHERE v.user.id = :userId")
    List<Visit> getAllVisits(@Param("userId") Long userId);

    // TODO FIX THIS

}
