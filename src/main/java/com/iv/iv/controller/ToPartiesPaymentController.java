package com.iv.iv.controller;

import com.iv.iv.entity.Challan;
import com.iv.iv.entity.ChallanToParties;
import com.iv.iv.entity.ToPartiesPayments;
import com.iv.iv.repository.ChallanToPartiesRepository;
import com.iv.iv.repository.ToPartiesPaymentsRepository;
import com.iv.iv.service.ToPartiesPaymentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/tp/payments")
public class ToPartiesPaymentController {
    @Autowired
    private ToPartiesPaymentsService toPartiesPaymentsService;

    @CrossOrigin(origins = "http://localhost:3000")  // Allow CORS for React app only
    @PostMapping("/paid")
    public ResponseEntity<?> makePayment(@RequestBody ToPartiesPayments request) {
        ToPartiesPayments toPartiesPayments = toPartiesPaymentsService.recordToPartyPayment(request);
     return ResponseEntity.ok(toPartiesPayments);
    }

}
