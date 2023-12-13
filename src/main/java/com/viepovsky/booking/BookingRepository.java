package com.viepovsky.booking;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
interface BookingRepository extends CrudRepository<Booking, Long> {
    List<Booking> findBookingsByDateAndStatusAndGarageId(LocalDate date, BookingStatus status, Long garageId);

    List<Booking> findBookingsByDateAndGarageId(LocalDate date, Long garageId);

    List<Booking> findBookingsByCarRepairListUserId(Long userId);
}
