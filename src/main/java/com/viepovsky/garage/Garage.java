package com.viepovsky.garage;

import com.viepovsky.audit.BaseEntityAudit;
import com.viepovsky.booking.Visit;
import com.viepovsky.garage.available_car_repair.ServiceCatalog;
import com.viepovsky.garage.garage_work_time.GarageSchedule;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Garage")
@Table(name = "garage")
public class Garage extends BaseEntityAudit {
    @Id
    @SequenceGenerator(
            name = "garage_id_sequence",
            sequenceName = "garage_id_sequence",
            initialValue = 5000,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "garage_id_sequence")
    private Long id;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @OneToOne(
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(
            name = "address_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "garage_address_id_fk"))
    private Address address;

    @OneToMany(
            targetEntity = Visit.class,
            mappedBy = "garage",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<Visit> visits = new ArrayList<>();

    @OneToMany(
            targetEntity = GarageSchedule.class,
            mappedBy = "garage",
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE,
                CascadeType.REFRESH,
                CascadeType.REMOVE
            })
    private List<GarageSchedule> garageSchedule = new ArrayList<>();

    @OneToMany(
            targetEntity = ServiceCatalog.class,
            mappedBy = "garage",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<ServiceCatalog> availableServices = new ArrayList<>();

    public Garage(String name, Address address) {
        this.name = name;
        this.address = address;
    }

    public Garage(
            String name,
            Address address,
            List<Visit> visits,
            List<GarageSchedule> garageSchedule,
            List<ServiceCatalog> availableServices) {
        this.name = name;
        this.address = address;
        this.visits = visits;
        this.garageSchedule = garageSchedule;
        this.availableServices = availableServices;
    }
}
