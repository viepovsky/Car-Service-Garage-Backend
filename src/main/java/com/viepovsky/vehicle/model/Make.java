package com.viepovsky.vehicle.model;

import com.viepovsky.utility.entity_audit.BaseEntityAudit;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "Make")
@Table(name = "make")
public class Make extends BaseEntityAudit {
    @Id
    @SequenceGenerator(
            name = "make_id_sequence",
            sequenceName = "make_id_sequence",
            initialValue = 5000,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "make_id_sequence")
    private Long id;

    @Column(name = "name", length = 128, nullable = false)
    private String name;
}
