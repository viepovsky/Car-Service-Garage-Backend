package com.viepovsky.car;

import com.viepovsky.audit.BaseEntityAudit;
import com.viepovsky.car_repair.CarRepair;
import com.viepovsky.user.AppUser;
import jakarta.persistence.*;
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
@Entity(name = "Vehicle")
@Table(name = "vehicle")
public class Vehicle extends BaseEntityAudit {

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

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "car_user_id_fk")
    )
    private AppUser user;

    @OneToMany(
            targetEntity = CarRepair.class,
            mappedBy = "car",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    )
    private List<CarRepair> carServicesList = new ArrayList<>();

    public Vehicle(Long id,
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

    public Vehicle(String make,
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
