package com.backend.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CARS")
public class Car {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "production_year")
    private int year;

    @Column(name = "engine")
    private String engine;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToMany(
            targetEntity = CarService.class,
            mappedBy = "customerCar",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<CarService> carServicesList = new ArrayList<>();

    public Car(Long id, String make, String model, int year, String engine) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.engine = engine;
    }
}
