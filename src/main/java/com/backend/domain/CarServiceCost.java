package com.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

    @Column(name = "cost")
    private Long cost;

    @Column(name = "make_multiplier")
    private Long makeMultiplier;

    @OneToOne(fetch = FetchType.EAGER)
    private CarService carService;
}
