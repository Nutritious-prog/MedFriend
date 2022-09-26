package com.nutritiousprog.medfriend.controllers;

import com.nutritiousprog.medfriend.model.Treatment;
import com.nutritiousprog.medfriend.services.TreatmentService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
public class TreatmentController {
    private final TreatmentService treatmentService;

    @PostMapping("/treatments")
    public Treatment createTreatment(@RequestBody Treatment treatment) {
        return treatmentService.create(treatment);
    }

    @GetMapping("/treatments")
    public List<Treatment> getAllTreatments() {
        return treatmentService.getAll();
    }

    @DeleteMapping("/treatments/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteTreatment(@PathVariable Long id) {
        boolean deleted = false;
        deleted = treatmentService.delete(id);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", deleted);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/treatments/{id}")
    public ResponseEntity<Treatment> getTreatmentById(@PathVariable Long id) {
        Treatment treatment = treatmentService.getById(id);
        return ResponseEntity.ok(treatment);
    }

    @PutMapping("/treatments/{id}")
    public ResponseEntity<Treatment> updateTreatment(@PathVariable Long id, @RequestBody Treatment treatment) {
        treatment = treatmentService.update(id, treatment);

        return ResponseEntity.ok(treatment);
    }
}
