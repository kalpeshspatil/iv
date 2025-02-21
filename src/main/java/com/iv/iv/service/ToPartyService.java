package com.iv.iv.service;

import com.iv.iv.entity.ToParty;
import com.iv.iv.repository.ToPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToPartyService {

    @Autowired
    private ToPartyRepository toPartyRepository;

    public List<ToParty> getAllCustomers() {
        return (List<ToParty>) toPartyRepository.findAll();
    }

    public Optional<ToParty> getCustomerById(Long id) {
        return toPartyRepository.findById(id);
    }

    public ToParty saveCustomer(ToParty toParty) {
        return toPartyRepository.save(toParty);
    }

    public ToParty updateCustomer(Long id, ToParty toPartyDetails) {
        Optional<ToParty> optionalCustomer = toPartyRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            ToParty toParty = optionalCustomer.get();

            toParty.setCustomerName(toPartyDetails.getCustomerName());
            toParty.setIsDeleted(toPartyDetails.getIsDeleted());
            return toPartyRepository.save(toParty);
        } else {
            throw new RuntimeException("Customer not found");
        }
    }

    public void deleteCustomer(Long id) {
        toPartyRepository.deleteById(id);
    }
}
