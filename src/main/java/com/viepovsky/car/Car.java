package com.viepovsky.car;

import com.viepovsky.audit.BaseEntityAudit;
import com.viepovsky.car_repair.CarRepair;
import com.viepovsky.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "CARS")
public class Car extends BaseEntityAudit {

    @Id
    @SequenceGenerator(
            name = "car_id_sequence",
            sequenceName = "car_id_sequence",
            initialValue = 5000,
            allocationSize = 100
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "car_id_sequence"
    )
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

    public Car(Long id,
               String make,
               String model,
               String type,
               int year,
               String engine) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.type = type;
        this.year = year;
        this.engine = engine;
    }

    public Car(String make,
               String model,
               String type,
               int year,
               String engine) {
        this.make = make;
        this.model = model;
        this.type = type;
        this.year = year;
        this.engine = engine;
    }
}
