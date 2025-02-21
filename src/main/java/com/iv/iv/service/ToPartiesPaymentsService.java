package com.iv.iv.service;

import com.iv.iv.entity.ChallanToParties;
import com.iv.iv.entity.ToPartiesPayments;
import com.iv.iv.repository.ChallanToPartiesRepository;
import com.iv.iv.repository.ToPartiesPaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ToPartiesPaymentsService {

    @Autowired
    private ToPartiesPaymentsRepository paymentRepository;

    @Autowired
    private ChallanToPartiesRepository toPartiesRepository;

    public ToPartiesPayments recordToPartyPayment(ToPartiesPayments request) {
        try {
            Optional<ChallanToParties> toPartyOptional = toPartiesRepository.findById(Long.valueOf(request.getChallanToParties().getPkId()));
            ToPartiesPayments paymentRecorded = null;
            if (toPartyOptional.isPresent()) {
                ChallanToParties toParty = toPartyOptional.get();

                // Insert into ToPartiesPayment table
                ToPartiesPayments payment = new ToPartiesPayments();
                payment.setPaymentDate(request.getPaymentDate());
                payment.setChallanToParties(request.getChallanToParties());
                payment.setPayment(request.getPayment());

                paymentRecorded = paymentRepository.save(payment);

                // Update remaining balance in ToParties table
                double newBalance = toParty.getOutstandingPayment() - request.getPayment();
                if(newBalance == 0){
                    toParty.setPaymentStatus("RECEIVED");
                }
                toParty.setOutstandingPayment((long) Math.max(0, newBalance)); // Ensure balance doesn't go negative

                toPartiesRepository.save(toParty);
            }
            return paymentRecorded;
        } catch (Exception exception) {
            throw exception;
        }
    }
}
