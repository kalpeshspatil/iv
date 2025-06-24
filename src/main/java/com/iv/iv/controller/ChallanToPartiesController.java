package com.iv.iv.controller;

import com.iv.iv.dto.HomePageStatisticsDTO;
import com.iv.iv.entity.Challan;
import com.iv.iv.entity.ChallanToParties;
import com.iv.iv.entity.ToPartiesLedgerEntries;
import com.iv.iv.service.ChallanService;
import com.iv.iv.service.ChallanToPartiesService;
import com.iv.iv.utility.IvConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/challanToParties")
@CrossOrigin(origins = {"http://localhost:3000", "https://iv.dakshabhi.com"})  // Allow CORS for React app only
public class ChallanToPartiesController {

    @Autowired
    private ChallanToPartiesService challanToPartiesService;

    @GetMapping
    public List<ChallanToParties> getAllChallanToParties() {
        return challanToPartiesService.getAllChallanToParties();
    }

    @Autowired
    private ChallanService challanService;

    @PutMapping("/updateStatus")
    public ResponseEntity<ChallanToParties> updateStatus(@RequestBody ChallanToParties challanToParties) {
        Optional<ChallanToParties> optionalChallanToParties = challanToPartiesService.getPartyById((long) challanToParties.getChallanTpPkid());

        if (optionalChallanToParties.isPresent()) {
            ChallanToParties existingParty = optionalChallanToParties.get();

            existingParty.setOrderStatus(challanToParties.getOrderStatus());

            // Prevent nested challan from overriding vehicle number or delivery date
            Challan challanRef = new Challan();
            challanRef.setChallanId(challanToParties.getChallan().getChallanId());
            challanRef.setProduct(challanToParties.getChallan().getProduct());
            //challanRef.setOrderPlacedDate(challanToParties.getChallan().getOrderPlacedDate());
            existingParty.setChallan(challanRef);
            // Save party before challan is updated
            ChallanToParties updatedChallan = challanToPartiesService.saveProduct(existingParty);

            if ("DELIVERED".equalsIgnoreCase(challanToParties.getOrderStatus())) {
                    String vehicleNumber = challanToParties.getVehicleNumber();
                    Long challanId = challanToParties.getChallan().getChallanId(); // Get ID early
                    Optional<Challan> challanOpt = challanService.getChallanById(challanId);
                    if (challanOpt.isPresent()) {
                        Challan challan = challanOpt.get();
                        if (vehicleNumber != null && !vehicleNumber.trim().isEmpty()) {
                            challan.setVehicleNumber(vehicleNumber);
                        }
                        challan.setOrderDeliveryDate(challanToParties.getDeliveryDate());
                        challanService.updateChallan(challan);
                    }
                existingParty.getChallan().setOrderDeliveryDate(challanToParties.getDeliveryDate());
                challanToPartiesService.recordLedgerEntries(existingParty);
            } else if ("PENDING".equalsIgnoreCase(challanToParties.getOrderStatus()) || "CANCELLED".equalsIgnoreCase(challanToParties.getOrderStatus())) {
                challanToPartiesService.markLedgerEntryAsVoided(challanToParties);

            }
            return ResponseEntity.ok(updatedChallan);
        }

        return ResponseEntity.notFound().build();
    }



    @PutMapping("/updatePaymentStatus")
    public ResponseEntity<ChallanToParties> updatePaymentStatus(@RequestBody ChallanToParties challanToParties) {
        Optional<ChallanToParties> optionalChallanToParties = challanToPartiesService.getPartyById((long) challanToParties.getChallanTpPkid());
        ChallanToParties updatedChallan = null;
        if (optionalChallanToParties.isPresent()){
            optionalChallanToParties.get().setPaymentStatus(challanToParties.getPaymentStatus());
            updatedChallan = challanToPartiesService.saveProduct(optionalChallanToParties.get());
        }
        // Call the service to update the statu
        return ResponseEntity.ok(updatedChallan); // Return updated entity
    }

    @GetMapping("/homePageStat")
    public HomePageStatisticsDTO getHomePageStaistics() {
        return challanToPartiesService.getHomePageStaistics();
    }

}
