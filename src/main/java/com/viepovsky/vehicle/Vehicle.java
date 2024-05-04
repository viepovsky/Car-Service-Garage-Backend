package com.viepovsky.vehicle;

import com.viepovsky.audit.BaseEntityAudit;
import com.viepovsky.user.AppUser;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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

    public Vehicle(Long id, Model model, int manufactured_year) {
        this.id = id;
        this.model = model;
        this.manufactured_year = manufactured_year;
    }

    public Vehicle(Model model, int manufactured_year) {
        this.model = model;
        this.manufactured_year = manufactured_year;
    }
}
