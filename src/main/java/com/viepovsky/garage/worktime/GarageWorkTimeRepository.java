package com.viepovsky.garage.worktime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface GarageWorkTimeRepository extends JpaRepository<GarageWorkTime, Long> {
    List<GarageWorkTime> findAllByGarageId(Long id);
}
