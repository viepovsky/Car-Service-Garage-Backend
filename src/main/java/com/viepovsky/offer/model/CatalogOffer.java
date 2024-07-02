package com.viepovsky.offer.model;

import com.viepovsky.garage.model.Garage;
import com.viepovsky.utility.entity_audit.BaseEntityAudit;

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
@Entity(name = "CatalogOffer")
@Table(name = "catalog_offer")
public class CatalogOffer extends BaseEntityAudit {
    @Id
    @SequenceGenerator(
            name = "catalog_offer_id_sequence",
            sequenceName = "catalog_offer_id_sequence",
            initialValue = 5000,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "catalog_offer_id_sequence")
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
            foreignKey = @ForeignKey(name = "catalog_offer_garage_id_fk"))
    private Garage garage;

    @OneToMany(
            targetEntity = SelectedOffer.class,
            mappedBy = "catalogOffer",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    private List<SelectedOffer> selectedOffers = new ArrayList<>();

    public CatalogOffer(
            String name,
            String description,
            BigDecimal price,
            int probableRepairTime) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.probableRepairTime = probableRepairTime;
    }
}
