package com.viepovsky.garage.worktime;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
interface GarageWorkTimeRepository extends CrudRepository<GarageWorkTime, Long> {
    List<GarageWorkTime> findAll();

    List<GarageWorkTime> findAllByGarageId(Long id);

    Optional<GarageWorkTime> findById(Long id);
}
