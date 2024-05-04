package com.viepovsky.car_repair;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface CarRepairRepository extends JpaRepository<ServiceSelected, Long> {
//    List<CarRepair> findCarServicesByUserId(Long userId);
    List<ServiceSelected> findCarServicesByName(Long valueToChangeTODO);

}
