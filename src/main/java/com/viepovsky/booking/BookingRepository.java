package com.viepovsky.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
interface BookingRepository extends JpaRepository<Visit, Long> {
    List<Visit> findBookingsByDateAndStatusAndGarageId(LocalDate date, BookingStatus status, Long garageId);

    List<Visit> findBookingsByDateAndGarageId(LocalDate date, Long garageId);

//    List<Booking> findBookingsByCarRepairListUserId(Long userId);
    List<Visit> findBookingsByCarRepairList(Long valueToChangeTODO);

}
