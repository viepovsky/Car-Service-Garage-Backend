package com.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CARS_SERVICES_COSTS")
public class CarServiceCost {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "cost", nullable = false)
    private Long cost;

    @Column(name = "repair_time")
    private Time repairTime;

    @Column(name = "make_multiplier")
    private Long makeMultiplier;

    @OneToMany(
            targetEntity = CarService.class,
            mappedBy = "carServiceCost",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<CarService> carServicesList;
}
