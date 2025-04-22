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
            Optional<ChallanToParties> toPartyOptional = fetchChallanToParty(request);
            if (toPartyOptional.isEmpty()) return null;

            ChallanToParties toParty = toPartyOptional.get();

            ToPartiesPayments savedPayment = savePaymentRecord(request);
            updateOutstandingPayment(toParty, request);
            saveLedgerEntry(toParty, request);

            return savedPayment;
        } catch (Exception e) {
            throw e;
        }
    }

    private Optional<ChallanToParties> fetchChallanToParty(ToPartiesPayments request) {
        Long pkId = Long.valueOf(request.getChallanToParties().getPkId());
        return toPartiesRepository.findById(pkId);
    }

    private ToPartiesPayments savePaymentRecord(ToPartiesPayments request) {
        ToPartiesPayments payment = new ToPartiesPayments();
        payment.setPaymentDate(request.getPaymentDate());
        payment.setChallanToParties(request.getChallanToParties());
        payment.setPayment(request.getPayment());

        return paymentRepository.save(payment);
    }

    private void updateOutstandingPayment(ChallanToParties toParty, ToPartiesPayments request) {
        BigDecimal newBalance = toParty.getOutstandingPayment().subtract(request.getPayment());

        if (newBalance.compareTo(BigDecimal.ZERO) == 0) {
            toParty.setPaymentStatus(IvConstants.RECIEVED);
        }

        toParty.setOutstandingPayment(newBalance.max(BigDecimal.ZERO));
        toPartiesRepository.save(toParty);
    }

    private void saveLedgerEntry(ChallanToParties toParty, ToPartiesPayments request) {
        ToPartiesLedgerEntries ledgerEntry = new ToPartiesLedgerEntries();

        ledgerEntry.setTpCustomerId(toParty.getSelectedToParty());
        ledgerEntry.setDate(request.getPaymentDate());
        ledgerEntry.setCredit(request.getPayment());

        BigDecimal currentBalance = configUtility.getCustomerBalance(toParty.getSelectedToParty().getTpCustomerId());
        ledgerEntry.setBalance(currentBalance.subtract(request.getPayment()));

        ledgerEntry.setType(IvConstants.CREDIT);

        toPartiesIndividualLedgerRepository.save(ledgerEntry);
    }

}
