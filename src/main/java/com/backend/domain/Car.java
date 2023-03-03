package com.backend.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CARS")
public class Car {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "make", nullable = false)
    private String make;
    @Column(name = "model", nullable = false)
    private String model;
    @Column(name = "year", nullable = false)
    private int year;
    @Column(name = "engine")
    private String engine;
    @OneToMany(targetEntity = CarService.class, mappedBy = "customerCar", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CarService> carServicesList;
}
