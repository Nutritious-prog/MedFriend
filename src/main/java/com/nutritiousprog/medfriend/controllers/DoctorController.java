package com.nutritiousprog.medfriend.controllers;

import com.nutritiousprog.medfriend.model.Doctor;
import com.nutritiousprog.medfriend.services.DoctorService;
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
public class DoctorController {
    private DoctorService doctorService;

    @PostMapping("/doctors")
    public Doctor createPatient(@RequestBody Doctor doctor) {
        return doctorService.create(doctor);
    }

    @GetMapping("/doctors")
    public List<Doctor> getAllPatients() {
        return doctorService.getAll();
    }

    @DeleteMapping("/doctors/{id}")
    public ResponseEntity<Map<String, Boolean>> deletePatient(@PathVariable Long id) {
        boolean deleted = false;
        deleted = doctorService.delete(id);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", deleted);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/doctors/{id}")
    public ResponseEntity<Doctor> getPatientById(@PathVariable Long id) {
        Doctor doctor = doctorService.getById(id);
        return ResponseEntity.ok(doctor);
    }

    @Transactional
    @PutMapping("/doctors/{id}")
    public ResponseEntity<Doctor> updatePatient(@PathVariable Long id, @RequestBody Doctor doctor) {
        doctor = doctorService.update(id, doctor);

        return ResponseEntity.ok(doctor);
    }
}
