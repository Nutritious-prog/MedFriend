package com.nutritiousprog.medfriend.controllers;

import com.nutritiousprog.medfriend.model.Patient;
import com.nutritiousprog.medfriend.services.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
public class PatientController {
    private final PatientService patientService;

    @PostMapping("/patients")
    public Patient createPatient(@RequestBody Patient patient) {
        return patientService.create(patient);
    }

    @GetMapping("/patients")
    public List<Patient> getAllPatients() {
        return patientService.getAll();
    }

    @DeleteMapping("/patients/{id}")
    public ResponseEntity<Map<String, Boolean>> deletePatient(@PathVariable Long id) {
        boolean deleted = false;
        deleted = patientService.delete(id);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", deleted);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/patients/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable Long id) {
        Patient patient = patientService.getById(id);
        return ResponseEntity.ok(patient);
    }

    @Transactional
    @PutMapping("/patients/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable Long id, @RequestBody Patient patient) {
        patient = patientService.update(id, patient);

        return ResponseEntity.ok(patient);
    }
}
