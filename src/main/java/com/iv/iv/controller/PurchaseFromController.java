package com.iv.iv.controller;

import com.iv.iv.entity.PurchaseFrom;
import com.iv.iv.service.PurchaseFromService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/pf/customers")
public class PurchaseFromController {

    @Autowired
    private PurchaseFromService purchaseFromService;

    @GetMapping
    @CrossOrigin(origins = {"http://localhost:3000", "https://iv.dakshabhi.com"})  // Allow CORS for React app only
    public List<PurchaseFrom> getAllCustomers() {
        return purchaseFromService.getAllCustomers();
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = {"http://localhost:3000", "https://iv.dakshabhi.com"})  // Allow CORS for React app only
    public ResponseEntity<PurchaseFrom> getCustomerById(@PathVariable Long id) {
        Optional<PurchaseFrom> customer = purchaseFromService.getCustomerById(id);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public PurchaseFrom createCustomer(@RequestBody PurchaseFrom purchaseFrom) {
        return purchaseFromService.saveCustomer(purchaseFrom);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PurchaseFrom> updatePfCustomer(@RequestBody PurchaseFrom purchaseFromDetails) {
        try {
            PurchaseFrom updatedPurchaseFrom = purchaseFromService.updatePfCustomer(purchaseFromDetails);
            return ResponseEntity.ok(updatedPurchaseFrom);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        purchaseFromService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
