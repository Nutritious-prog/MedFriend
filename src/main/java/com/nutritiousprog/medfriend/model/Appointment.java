package com.nutritiousprog.medfriend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Appointment")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "patient_id", referencedColumnName = "ID")
    private Patient patient;
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "doctor_id", referencedColumnName = "ID")
    private Doctor doctor;
    @OneToMany(cascade = {CascadeType.ALL})
    @JoinColumn(name = "treatment_id", referencedColumnName = "ID")
    private List<Treatment> treatment;
    @Column(name = "date")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateTime;

    public Appointment(Patient patient, Doctor doctor, List<Treatment> treatment, LocalDateTime dateTime) {
        this.patient = patient;
        this.doctor = doctor;
        this.treatment = treatment;
        this.dateTime = dateTime;
    }
}
