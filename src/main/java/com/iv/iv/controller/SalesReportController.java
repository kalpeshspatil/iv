package com.iv.iv.controller;

import com.iv.iv.dto.RetailerSalesReportRequest;
import com.iv.iv.dto.SalesReportRequest;
import com.iv.iv.dto.SalesReportResponseDTO;
import com.iv.iv.service.SalesReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("sales/report")
public class SalesReportController {

    @Autowired
    SalesReportService salesReportService;



    @CrossOrigin(origins = {"http://localhost:3000", "https://iv.dakshabhi.com"})
    @PostMapping
    public ResponseEntity<?> generateSalesReport(@RequestBody SalesReportRequest request) {
        try {
            SalesReportResponseDTO report = salesReportService.generateSalesReport(request);
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage(), "status", false));
        }
    }

    @CrossOrigin(origins = {"http://localhost:3000", "https://iv.dakshabhi.com"})
    @PostMapping("/retailer")
    public ResponseEntity<?> generateSalesReport(@RequestBody RetailerSalesReportRequest request) {
        try {
            SalesReportResponseDTO report = salesReportService.generateRetailerSalesReport(request);
            return ResponseEntity.ok(report);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage(), "status", false));
        }
    }
}
