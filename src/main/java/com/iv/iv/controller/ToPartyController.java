package com.iv.iv.controller;

import com.iv.iv.entity.PurchaseFrom;
import com.iv.iv.entity.ToParty;
import com.iv.iv.service.PurchaseFromService;
import com.iv.iv.service.ToPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tp/customers")
public class ToPartyController {

    @Autowired
    private ToPartyService toPartyService;

    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")  // Allow CORS for React app only
    public List<ToParty> getAllCustomers() {
        return toPartyService.getAllCustomers();
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = "http://localhost:3000")  // Allow CORS for React app only
    public ResponseEntity<ToParty> getCustomerById(@PathVariable Long id) {
        Optional<ToParty> customer = toPartyService.getCustomerById(id);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ToParty createCustomer(@RequestBody ToParty toParty) {
        return toPartyService.saveCustomer(toParty);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToParty> updateCustomer(@PathVariable Long id, @RequestBody ToParty toPartyDetails) {
        try {
            ToParty updatedToParty = toPartyService.updateCustomer(id, toPartyDetails);
            return ResponseEntity.ok(updatedToParty);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        toPartyService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
