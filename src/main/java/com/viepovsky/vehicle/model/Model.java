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
@Entity(name = "Model")
@Table(name = "model")
public class Model extends BaseEntityAudit {
    @Id
    @SequenceGenerator(
            name = "model_id_sequence",
            sequenceName = "model_id_sequence",
            initialValue = 5000,
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "model_id_sequence")
    private Long id;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(
            name = "make_id",
            referencedColumnName = "id",
            nullable = false,
            foreignKey = @ForeignKey(name = "model_make_id_fk"))
    private Make make;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private Type type;
}
