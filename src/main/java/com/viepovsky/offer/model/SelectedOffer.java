package com.viepovsky.offer.model;

import com.viepovsky.user.model.AppUser;
import com.viepovsky.utility.entity_audit.BaseEntityAudit;
import com.viepovsky.visit.model.Visit;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "SelectedOffer")
@Table(name = "selected_offer")
public class SelectedOffer extends BaseEntityAudit {
    @Id
    @SequenceGenerator(
            name = "selected_offer_id_sequence",
            sequenceName = "selected_offer_id_sequence",
            initialValue = 5000,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "selected_offer_id_sequence")
    private Long id;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "discount")
    private BigDecimal discount;

    @Column(name = "repair_time", nullable = false)
    private int probableRepairTime;

    @Column(name = "repair_status", nullable = false)
    private RepairStatus status;

    @Column(name = "description", nullable = false, columnDefinition = "text")
    private String details;

    @ManyToOne
    @JoinColumn(
            name = "visit_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "selected_offer_visit_id_fk"))
    private Visit visit;

    @ManyToOne
    @JoinColumn(
            name = "catalog_offer_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "selected_offer_catalog_id"))
    private CatalogOffer catalogOffer;

    public SelectedOffer(
            String details,
            BigDecimal price,
            int probableRepairTime,
            AppUser user,
            Visit visit,
            RepairStatus status) {
        this.details = details;
        this.price = price;
        this.probableRepairTime = probableRepairTime;
        this.visit = visit;
        this.status = status;
    }
}
