package com.iv.iv.service;

import com.iv.iv.entity.*;
import com.iv.iv.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChallanService {

    @Autowired
    private ChallanRepository challanRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PurchaseFromRepository purchaseFromRepository;

    @Autowired
    private ToPartyRepository toPartyRepository;

    public List<Challan> getAllChallans() {
        return (List<Challan>) challanRepository.findAll();
    }

    public Optional<Challan> getChallanById(Long id) {
        return challanRepository.findById(id);
    }

    public Challan saveChallan(Challan challan) {
        Optional<Product> product = productRepository.findById(challan.getProduct().getProductId());
        if (product.isPresent()){
            challan.setProduct(product.get());
        }
        Optional<PurchaseFrom> existingPurchaseFrom = purchaseFromRepository.findById(challan.getPurchaseFrom().getPfCustomerId());
        if (existingPurchaseFrom.isPresent()){
            challan.setPurchaseFrom(existingPurchaseFrom.get());
        }

        // Ensure each ChallanToParties entry has a valid ToParty reference
        List<ChallanToParties> updatedChallanToParties = new ArrayList<>();
        for (ChallanToParties challanToParties : challan.getChallanToParties()) {
            ToParty existingToParty = toPartyRepository.findById(challanToParties.getSelectedToParty().getTpCustomerId())
                    .orElseThrow(() -> new RuntimeException("ToParty not found"));
            challanToParties.setSelectedToParty(existingToParty);
            challanToParties.setChallan(challan);
            challanToParties.setGrossAmount((long) (challanToParties.getChallanToPartiesQty() * challanToParties.getChallanToPartiesRate()));
            challanToParties.setOutstandingPayment((long) (challanToParties.getChallanToPartiesQty() * challanToParties.getChallanToPartiesRate()));
            updatedChallanToParties.add(challanToParties);
        }
        challan.setChallanToParties(updatedChallanToParties);

        return challanRepository.save(challan);
    }

    public Challan updateChallan(Challan challanDetails) {
        Optional<Challan> optionalChallan = challanRepository.findById(challanDetails.getChallanId());
        if (optionalChallan.isPresent()) {
            Challan challan = optionalChallan.get();

            // Update basic details
            challan.setProduct(challanDetails.getProduct());
            challan.setPurchaseFrom(challanDetails.getPurchaseFrom());
            challan.setOrderDeliveryDate(challanDetails.getOrderDeliveryDate());
            challan.setOrderPlacedDate(challanDetails.getOrderPlacedDate());
            challan.setVehicleNumber(challanDetails.getVehicleNumber());
            challan.setPurchaseFromRate(challanDetails.getPurchaseFromRate());
            challan.setPurchaseFromQuantity(challanDetails.getPurchaseFromQuantity());
            challan.setDeleted(challanDetails.getDeleted());
            challan.setOrderDeliveryDate(challanDetails.getOrderDeliveryDate());

            // Update ToParties
            if (challanDetails.getChallanToParties() != null) {
                // Clear existing toParties to remove orphaned entities
                challan.getChallanToParties().clear();

                // Add the updated list of toParties
                challan.getChallanToParties().addAll(challanDetails.getChallanToParties());
            }

            // Save the updated entity
            return challanRepository.save(challan);
        } else {
            throw new RuntimeException("Challan not found with ID: " + challanDetails.getChallanId());
        }
    }

    public Challan updateChallanStatus(Long challanId, String status) {
        Optional<Challan> optionalChallan = challanRepository.findById(challanId);
        if (optionalChallan.isPresent()) {
            Challan challan = optionalChallan.get();
            challan.setChallanStatus(status);
            challan.setOrderDeliveryDate(LocalDateTime.now());
            return challanRepository.save(challan);
        } else {
            throw new RuntimeException("Challan not found");
        }
    }


    public void deleteChallan(Long id) {
        challanRepository.deleteById(id);
    }
}

