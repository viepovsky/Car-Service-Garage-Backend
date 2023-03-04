package com.backend.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Time;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GARAGE_WORK_TIMES")
public class GarageWorkTime {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "day")
    private WorkDays day;

    @Column(name = "start_hour")
    private Time starHour;

    @Column(name = "end_hour")
    private Time endHour;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "garage_id")
    private Garage garage;

}
