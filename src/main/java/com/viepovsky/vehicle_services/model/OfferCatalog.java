package com.viepovsky.vehicle_services.model;

import com.viepovsky.audit.BaseEntityAudit;
import com.viepovsky.car_repair.OfferSelected;
import com.viepovsky.garage.model.Garage;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "OfferCatalog")
@Table(name = "offer_catalog")
public class OfferCatalog extends BaseEntityAudit {
    @Id
    @SequenceGenerator(
            name = "offer_catalog_id_sequence",
            sequenceName = "offer_catalog_id_sequence",
            initialValue = 5000,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "offer_catalog_id_sequence")
    private Long id;

    @Column(name = "name", nullable = false, length = 128)
    private String name;

    @Column(name = "description", columnDefinition = "text")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "repair_time")
    private int probableRepairTime;

    @ManyToOne
    @JoinColumn(
            name = "garage_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "offer_catalog_garage_id_fk"))
    private Garage garage;

    @OneToMany(
            targetEntity = OfferSelected.class,
            mappedBy = "offerCatalog",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<OfferSelected> selectedOffers = new ArrayList<>();

    public OfferCatalog(
            String name,
            String description,
            BigDecimal price,
            int probableRepairTime,
            Garage garage) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.probableRepairTime = probableRepairTime;
        this.garage = garage;
    }

    public OfferCatalog(OfferCatalog availableCarRepair) {
        this.id = availableCarRepair.getId();
        this.name = availableCarRepair.getName();
        this.description = availableCarRepair.getDescription();
        this.price = availableCarRepair.getPrice();
        this.probableRepairTime = availableCarRepair.getProbableRepairTime();
        this.garage = availableCarRepair.getGarage();
    }
}
