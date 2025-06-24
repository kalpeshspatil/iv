package com.iv.iv.controller;

import com.iv.iv.dto.ToPartyLedgerDto;
import com.iv.iv.entity.ToPartiesLedgerEntries;
import com.iv.iv.service.ToPartiesLedgerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/challanToParties/ledger")
@CrossOrigin(origins = {"http://localhost:3000", "https://iv.dakshabhi.com"})
public class ToPartiesLedgerController {

    @Autowired
    ToPartiesLedgerService toPartiesLedgerService;

    @GetMapping
    public ResponseEntity<List<ToPartyLedgerDto>> getAll() {
        try {
            List<ToPartyLedgerDto> list = toPartiesLedgerService.getAllToPartiesLedger();
            return ResponseEntity.ok(
                    list );
        } catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    @CrossOrigin(origins = {"http://localhost:3000", "https://iv.dakshabhi.com"})  // Allow CORS for React app only
    public ResponseEntity<List<ToPartiesLedgerEntries>> getLedgerByToPartyCustomer(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(toPartiesLedgerService.getLedgerByToPartyCustomer(id));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().build();
        }
    }


}
