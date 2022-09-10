package com.nutritiousprog.medfriend.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Appointment")
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "patient_id", referencedColumnName = "ID")
    private Patient patient;
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "treatment_id", referencedColumnName = "ID")
    private List<Treatment> treatment;

    public Appointment(Patient patient, List<Treatment> treatment) {
        this.patient = patient;
        this.treatment = treatment;
    }
}
