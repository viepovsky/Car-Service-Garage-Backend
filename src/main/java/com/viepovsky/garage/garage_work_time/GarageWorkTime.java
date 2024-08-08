package com.viepovsky.garage.garage_work_time;

import com.viepovsky.audit.BaseEntityAudit;
import com.viepovsky.garage.Garage;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GARAGE_WORK_TIMES")
public class GarageWorkTime extends BaseEntityAudit {

    @Id
    @SequenceGenerator(
            name = "garage_work_time_id_sequence",
            sequenceName = "garage_work_time_id_sequence",
            initialValue = 5000,
            allocationSize = 100
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "garage_work_time_id_sequence"
    )
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

    public GarageWorkTime(Long id,
                          WorkDays day,
                          LocalTime startHour,
                          LocalTime endHour) {
        this.id = id;
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
    }

}
