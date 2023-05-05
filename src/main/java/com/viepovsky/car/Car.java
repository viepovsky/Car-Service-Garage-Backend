package com.viepovsky.car;

import com.viepovsky.carservice.CarRepair;
import com.viepovsky.user.User;
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
@SequenceGenerator(name = "seq", initialValue = 5000, allocationSize = 100)
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;

    @Column(name = "make")
    private String make;

    @Column(name = "model")
    private String model;

    @Column(name = "production_year")
    private int year;

    @Column(name = "type")
    private String type;

    @Column(name = "engine")
    private String engine;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(
            targetEntity = CarRepair.class,
            mappedBy = "car",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY
    )
    private List<CarRepair> carServicesList = new ArrayList<>();

    public Car(Long id, String make, String model, String type, int year, String engine) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.type = type;
        this.year = year;
        this.engine = engine;
    }

    public Car(String make, String model, String type, int year, String engine) {
        this.make = make;
        this.model = model;
        this.type = type;
        this.year = year;
        this.engine = engine;
    }
}
