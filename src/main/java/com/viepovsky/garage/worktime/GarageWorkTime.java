package com.viepovsky.garage.worktime;

import com.viepovsky.garage.Garage;
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
@SequenceGenerator(name = "seq", initialValue = 5000, allocationSize = 100)
public class GarageWorkTime {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;
    @Column(name = "work_day")
    @Enumerated(EnumType.STRING)
    private WorkDays day;

    @Column(name = "start_hour")
    private LocalTime startHour;

    @Column(name = "end_hour")
    private LocalTime endHour;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "garage_id")
    private Garage garage;

    public GarageWorkTime(Long id, WorkDays day, LocalTime startHour, LocalTime endHour) {
        this.id = id;
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
    }

}
