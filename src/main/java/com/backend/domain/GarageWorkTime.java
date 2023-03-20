package com.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GARAGE_WORK_TIMES")
@SequenceGenerator(name="seq", initialValue=5000, allocationSize=100)
public class GarageWorkTime {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    private Long id;

    @Column(name = "work_day")
    private WorkDays day;

    @Column(name = "start_hour")
    private LocalTime startHour;

    @Column(name = "end_hour")
    private LocalTime endHour;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "garage_id")
    private Garage garage;

    public GarageWorkTime(WorkDays day, LocalTime startHour, LocalTime endHour, Garage garage) {
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
        this.garage = garage;
    }

    public GarageWorkTime(Long id, String day, LocalTime startHour, LocalTime endHour) {
        this.id = id;
        try {
            this.day = WorkDays.valueOf(day);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Invalid day value: " + day, exception);
        }
        this.startHour = startHour;
        this.endHour = endHour;
    }
    public GarageWorkTime(String day, LocalTime startHour, LocalTime endHour) {
        try {
            this.day = WorkDays.valueOf(day);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Invalid day value: " + day, exception);
        }
        this.startHour = startHour;
        this.endHour = endHour;
    }
}
