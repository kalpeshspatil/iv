package com.iv.iv.controller;

import com.iv.iv.entity.Challan;
import com.iv.iv.service.ChallanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/challan")
public class ChallanController {

    @Autowired
    private ChallanService challanService;

    @CrossOrigin(origins = "http://localhost:3000")  // Allow CORS for React app only
    @GetMapping
    public List<Challan> getAllChallans() {
        return challanService.getAllChallans();
    }

    @CrossOrigin(origins = "http://localhost:3000")  // Allow CORS for React app only
    @GetMapping("/{id}")
    public ResponseEntity<Challan> getChallanById(@PathVariable Long id) {
        Optional<Challan> challan = challanService.getChallanById(id);
        return challan.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @CrossOrigin(origins = "http://localhost:3000")  // Allow CORS for React app only
    @PostMapping
    public ResponseEntity<?> createChallan(@RequestBody Challan challan) {
        try {
            Challan savedChallan = challanService.saveChallan(challan);
            return ResponseEntity.ok(Map.of("status", true));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage(), "status", false));
        }
    }
    @CrossOrigin(origins = "http://localhost:3000")  // Allow CORS for React app only
    @PutMapping
    public ResponseEntity<Challan> updateChallan(@RequestBody Challan challanDetails) {
        try {
            Challan updatedChallan = challanService.updateChallan(challanDetails);
            return ResponseEntity.ok(updatedChallan);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChallan(@PathVariable Long id) {
        challanService.deleteChallan(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin(origins = "http://localhost:3000")  // Allow CORS for React app only
    @PutMapping("/updateStatus")
    public ResponseEntity<?> updateChallanStatus(@RequestBody Map<String, Object> requestBody) {
        try {
            Long challanId = Long.valueOf(requestBody.get("challanId").toString());
            String status = requestBody.get("status").toString();
            Challan updatedChallan = challanService.updateChallanStatus(challanId,status);
            return ResponseEntity.ok(updatedChallan);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage(), "status", false));
        }
    }
}
