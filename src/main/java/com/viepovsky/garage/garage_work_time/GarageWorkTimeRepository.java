package com.viepovsky.garage.garage_work_time;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface GarageWorkTimeRepository extends JpaRepository<GarageSchedule, Long> {
    List<GarageSchedule> findAllByGarageId(Long id);
}
