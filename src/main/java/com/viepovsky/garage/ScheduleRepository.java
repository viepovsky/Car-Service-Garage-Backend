package com.viepovsky.garage;

import com.viepovsky.garage.model.Schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByGarageId(Long id);

    @Query("SELECT s FROM GarageSchedule s WHERE s.date = :date AND s.garage.id = :garageId")
    Optional<Schedule> findByDateAndGarageId(
            @Param("date") LocalDate date, @Param("garageId") Long garageId);
}
