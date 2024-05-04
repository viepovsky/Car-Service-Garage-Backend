package com.viepovsky.garage;

import com.viepovsky.garage.model.Schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findAllByGarageId(Long id);
}
