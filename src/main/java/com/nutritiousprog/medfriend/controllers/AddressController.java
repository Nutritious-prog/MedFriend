package com.nutritiousprog.medfriend.controllers;

import com.nutritiousprog.medfriend.model.Address;
import com.nutritiousprog.medfriend.services.AddressService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
@AllArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @PostMapping("/addresses")
    public Address createTreatment(@RequestBody Address address) {
        return addressService.create(address);
    }

    @GetMapping("/addresses")
    public List<Address> getAllAddresses() {
        return addressService.getAll();
    }

    @DeleteMapping("/addresses/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteAddress(@PathVariable Long id) {
        boolean deleted = false;
        deleted = addressService.delete(id);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", deleted);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) {
        Address address = addressService.getById(id);
        return ResponseEntity.ok(address);
    }

    @PutMapping("/addresses/{id}")
    public ResponseEntity<Address> updateTreatment(@PathVariable Long id, @RequestBody Address address) {
        address = addressService.update(id, address);

        return ResponseEntity.ok(address);
    }
}
