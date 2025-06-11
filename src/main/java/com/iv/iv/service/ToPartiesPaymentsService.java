package com.iv.iv.service;

import com.iv.iv.entity.ChallanToParties;
import com.iv.iv.entity.ToPartiesLedgerEntries;
import com.iv.iv.entity.ToPartiesPayments;
import com.iv.iv.entity.ToParty;
import com.iv.iv.repository.ChallanToPartiesRepository;
import com.iv.iv.repository.ToPartiesIndividualLedgerRepository;
import com.iv.iv.repository.ToPartiesPaymentsRepository;
import com.iv.iv.repository.ToPartyRepository;
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
    private ToPartyRepository toPartyRepository;

    @Autowired
    private ToPartiesIndividualLedgerRepository toPartiesIndividualLedgerRepository;

    @Autowired
    IvConfigUtility configUtility;

    public ToPartiesPayments recordToPartyPayment(ToPartiesPayments request) {
        try {
            Optional<ToParty> toPartyOptional = fetchToParty(request);
            if (toPartyOptional.isEmpty()) return null;

            ToParty toParty = toPartyOptional.get();

            ToPartiesPayments savedPayment = savePaymentRecord(request);
           // updateOutstandingPayment(toParty, request);
            saveLedgerEntry(toParty, request);

            return savedPayment;
        } catch (Exception e) {
            throw e;
        }
    }

    private Optional<ToParty> fetchToParty(ToPartiesPayments request) {
        Long pkId = Long.valueOf(request.getToParty().getTpCustomerId());
        return toPartyRepository.findById(pkId);
    }

    private ToPartiesPayments savePaymentRecord(ToPartiesPayments request) {
        ToPartiesPayments payment = new ToPartiesPayments();
        payment.setPaymentDate(request.getPaymentDate());
        payment.setToParty(request.getToParty());
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

    private void saveLedgerEntry(ToParty toParty, ToPartiesPayments request) {
        ToPartiesLedgerEntries ledgerEntry = new ToPartiesLedgerEntries();

        ledgerEntry.setTpCustomerId(toParty);
        ledgerEntry.setDate(request.getPaymentDate());
        ledgerEntry.setCredit(request.getPayment());

        BigDecimal currentBalance = configUtility.getCustomerBalance(toParty.getTpCustomerId());
        ledgerEntry.setBalance(currentBalance.subtract(request.getPayment()));

        ledgerEntry.setType(IvConstants.CREDIT);

        toPartiesIndividualLedgerRepository.save(ledgerEntry);
    }

}
