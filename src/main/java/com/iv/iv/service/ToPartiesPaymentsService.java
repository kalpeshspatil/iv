package com.iv.iv.service;

import com.iv.iv.entity.ChallanToParties;
import com.iv.iv.entity.ToPartiesLedgerEntries;
import com.iv.iv.entity.ToPartiesPayments;
import com.iv.iv.repository.ChallanToPartiesRepository;
import com.iv.iv.repository.ToPartiesIndividualLedgerRepository;
import com.iv.iv.repository.ToPartiesPaymentsRepository;
import com.iv.iv.utility.IvConfigUtility;
import com.iv.iv.utility.IvConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ToPartiesPaymentsService {

    @Autowired
    private ToPartiesPaymentsRepository paymentRepository;

    @Autowired
    private ChallanToPartiesRepository toPartiesRepository;

    @Autowired
    private ToPartiesIndividualLedgerRepository toPartiesIndividualLedgerRepository;

    @Autowired
    IvConfigUtility configUtility;

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
                BigDecimal newBalance = toParty.getOutstandingPayment().subtract(request.getPayment());
                if(newBalance.compareTo(BigDecimal.ZERO) == 0){
                    toParty.setPaymentStatus(IvConstants.RECIEVED);
                }
                toParty.setOutstandingPayment(newBalance.max(BigDecimal.ZERO)); // Ensure balance doesn't go negative

                toPartiesRepository.save(toParty);


                ToPartiesLedgerEntries toPartiesLedgerEntries = new ToPartiesLedgerEntries();

                toPartiesLedgerEntries.setTpCustomerId(toParty.getSelectedToParty());
                toPartiesLedgerEntries.setDate(request.getPaymentDate());
                toPartiesLedgerEntries.setCredit(request.getPayment());
                toPartiesLedgerEntries.setBalance((configUtility.getCustomerBalance(toParty.getSelectedToParty().getTpCustomerId())).subtract(request.getPayment()));
                toPartiesLedgerEntries.setType(IvConstants.CREDIT);

                toPartiesIndividualLedgerRepository.save(toPartiesLedgerEntries);



            }
            return paymentRecorded;
        } catch (Exception exception) {
            throw exception;
        }
    }
}
