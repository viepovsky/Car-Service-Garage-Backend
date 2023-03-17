package com.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AVAILABLE_CAR_SERVICE")
public class AvailableCarService {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "cost", nullable = false)
    private BigDecimal cost;

    @Column(name = "repair_time")
    private int repairTimeInMinutes;

    @Column(name = "premium_make_list")
    private String premiumMakes;

    @Column(name = "make_multiplier")
    private BigDecimal makeMultiplier;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "garage_id")
    private Garage garage;

    public AvailableCarService(String name, String description, BigDecimal cost, int repairTimeInMinutes, String premiumMakes, BigDecimal makeMultiplier, Garage garage) {
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.repairTimeInMinutes = repairTimeInMinutes;
        this.premiumMakes = premiumMakes;
        this.makeMultiplier = makeMultiplier;
        this.garage = garage;
    }
    public AvailableCarService(AvailableCarService availableCarService) {
        this.id = availableCarService.getId();
        this.name = availableCarService.getName();
        this.description = availableCarService.getDescription();
        this.cost = availableCarService.getCost();
        this.repairTimeInMinutes = availableCarService.getRepairTimeInMinutes();
        this.premiumMakes = availableCarService.getPremiumMakes();
        this.makeMultiplier = availableCarService.getMakeMultiplier();
        this.garage = availableCarService.getGarage();
    }
}
