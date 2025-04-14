package com.iv.iv.utility;

import com.iv.iv.entity.ToPartiesLedgerEntries;
import com.iv.iv.repository.ToPartiesIndividualLedgerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class IvConfigUtility {

    @Autowired
    private ToPartiesIndividualLedgerRepository toPartiesIndividualLedgerRepository;
    public BigDecimal getCustomerBalance(Long customerId) {
        List<ToPartiesLedgerEntries> entries = toPartiesIndividualLedgerRepository.findByTpCustomerId(customerId);

        BigDecimal totalDebit = BigDecimal.ZERO;
        BigDecimal totalCredit = BigDecimal.ZERO;

        for (ToPartiesLedgerEntries entry : entries) {
            if (entry.getType().equals(IvConstants.DEBIT)) {
                totalDebit = totalDebit.add(entry.getDebit());
            } else if (entry.getType().equals(IvConstants.CREDIT)) {
                totalCredit = totalCredit.add(entry.getCredit());
            }
        }

        return totalDebit.subtract(totalCredit); // Balance = debit - credit
    }
}
