package com.viepovsky.vehicle.model;

import com.viepovsky.user.model.AppUser;
import com.viepovsky.utility.entity_audit.BaseEntityAudit;

import com.viepovsky.visit.model.Visit;
import jakarta.persistence.*;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Vehicle")
@Table(name = "vehicle")
public class Vehicle extends BaseEntityAudit {
    @Id
    @SequenceGenerator(
            name = "vehicle_id_sequence",
            sequenceName = "vehicle_id_sequence",
            initialValue = 5000,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicle_id_sequence")
    private Long id;

    @Column(name = "vin", length = 128)
    private String vin;

    @Column(name = "license_plate", nullable = false, length = 64)
    private String licensePlate;

    @ManyToOne
    @JoinColumn(
            name = "user_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "vehicle_user_id_fk"))
    private AppUser user;

    @ManyToOne
    @JoinColumn(
            name = "model_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "vehicle_model_id_fk"))
    private Model model;

    @Column(name = "engine_type")
    @Enumerated(EnumType.STRING)
    private EngineType engineType;

    @Column(name = "manufactured_year")
    private int manufactured_year;

    @Column(name = "details", columnDefinition = "text")
    private String details;

    @OneToMany(
            targetEntity = Visit.class,
            mappedBy = "vehicle",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Visit> visits = new ArrayList<>();

    public Vehicle(Long id, Model model, int manufactured_year) {
        this.id = id;
        this.model = model;
        this.manufactured_year = manufactured_year;
    }

    public Vehicle(Model model, int manufactured_year) {
        this.model = model;
        this.manufactured_year = manufactured_year;
    }

    public void updateFrom(Vehicle vehicle) {
        this.vin = vehicle.vin;
        this.licensePlate = vehicle.licensePlate;
        this.engineType = vehicle.engineType;
        this.manufactured_year = vehicle.manufactured_year;
        this.details = vehicle.details;
    }
}
