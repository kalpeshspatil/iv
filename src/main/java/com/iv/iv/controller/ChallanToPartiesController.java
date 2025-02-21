package com.iv.iv.controller;

import com.iv.iv.dto.ChallanToPartiesDto;
import com.iv.iv.dto.HomePageStatisticsDto;
import com.iv.iv.entity.ChallanToParties;
import com.iv.iv.entity.Product;
import com.iv.iv.service.ChallanToPartiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/challanToParties")
@CrossOrigin(origins = "http://localhost:3000")  // Allow CORS for React app only
public class ChallanToPartiesController {

    @Autowired
    private ChallanToPartiesService challanToPartiesService;

    @GetMapping
    public List<ChallanToParties> getAllChallanToParties() {
        return challanToPartiesService.getAllChallanToParties();
    }


    @PutMapping("/updateStatus")
    public ResponseEntity<ChallanToParties> updateStatus(@RequestBody ChallanToParties challanToParties) {
        Optional<ChallanToParties> optionalChallanToParties = challanToPartiesService.getPartyById((long) challanToParties.getPkId());
        ChallanToParties updatedChallan = null;
        if (optionalChallanToParties.isPresent()){
            optionalChallanToParties.get().setOrderStatus(challanToParties.getOrderStatus());
            if ("DELIVERED".equalsIgnoreCase(challanToParties.getOrderStatus())) {
                optionalChallanToParties.get().setDeliveryDate(LocalDateTime.now()); // Set current date-time
            }
            updatedChallan = challanToPartiesService.saveProduct(optionalChallanToParties.get());
        }
        // Call the service to update the statu
        return ResponseEntity.ok(updatedChallan); // Return updated entity
    }

    @PutMapping("/updatePaymentStatus")
    public ResponseEntity<ChallanToParties> updatePaymentStatus(@RequestBody ChallanToParties challanToParties) {
        Optional<ChallanToParties> optionalChallanToParties = challanToPartiesService.getPartyById((long) challanToParties.getPkId());
        ChallanToParties updatedChallan = null;
        if (optionalChallanToParties.isPresent()){
            optionalChallanToParties.get().setPaymentStatus(challanToParties.getPaymentStatus());
            updatedChallan = challanToPartiesService.saveProduct(optionalChallanToParties.get());
        }
        // Call the service to update the statu
        return ResponseEntity.ok(updatedChallan); // Return updated entity
    }

    @GetMapping("/homePageStat")
    public HomePageStatisticsDto getHomePageStaistics() {
        return challanToPartiesService.getHomePageStaistics();
    }

}
