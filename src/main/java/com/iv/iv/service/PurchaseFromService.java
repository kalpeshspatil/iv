package com.iv.iv.service;

import com.iv.iv.entity.PurchaseFrom;
import com.iv.iv.repository.PurchaseFromRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseFromService {

    @Autowired
    private PurchaseFromRepository purchaseFromRepository;

    public List<PurchaseFrom> getAllCustomers() {
        return (List<PurchaseFrom>) purchaseFromRepository.findAll();
    }

    public Optional<PurchaseFrom> getCustomerById(Long id) {
        return purchaseFromRepository.findById(id);
    }

    public PurchaseFrom saveCustomer(PurchaseFrom purchaseFrom) {
        return purchaseFromRepository.save(purchaseFrom);
    }

    public PurchaseFrom updatePfCustomer(PurchaseFrom purchaseFromDetails) {
        Optional<PurchaseFrom> optionalCustomer = purchaseFromRepository.findById(purchaseFromDetails.getPfCustomerId());
        if (optionalCustomer.isPresent()) {
            PurchaseFrom purchaseFrom = optionalCustomer.get();
            purchaseFrom.setPfCustomerName(purchaseFromDetails.getPfCustomerName());
            purchaseFrom.setIsDeleted(purchaseFromDetails.getIsDeleted());
            return purchaseFromRepository.save(purchaseFrom);
        } else {
            throw new RuntimeException("Customer not found");
        }
    }

    public void deleteCustomer(Long id) {
        purchaseFromRepository.deleteById(id);
    }
}
