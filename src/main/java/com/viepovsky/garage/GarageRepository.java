package com.viepovsky.garage;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface GarageRepository extends CrudRepository<Garage, Long> {
}
