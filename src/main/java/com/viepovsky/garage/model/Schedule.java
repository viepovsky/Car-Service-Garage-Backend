package com.viepovsky.garage.model;

import com.viepovsky.utility.entity_audit.BaseEntityAudit;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "GarageSchedule")
@Table(name = "garage_schedule")
public class Schedule extends BaseEntityAudit {
    @Id
    @SequenceGenerator(
            name = "garage_schedule_id_sequence",
            sequenceName = "garage_schedule_id_sequence",
            initialValue = 5000,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "garage_schedule_id_sequence")
    private Long id;

    @Column(name = "date", unique = true)
    private LocalDate day;

    @Column(name = "start_hour")
    private LocalTime openFrom;

    @Column(name = "end_hour")
    private LocalTime openTill;

    @ManyToOne
    @JoinColumn(
            name = "garage_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "schedule_garage_id_fk"))
    private Garage garage;
}
