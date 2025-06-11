package com.iv.iv.dto;


import java.math.BigDecimal;

//Interface Projection to map ledger query result automatically
public interface ToPartyLedgerDto {
    Long getTpCustomerId();
    String getCustomerName();
    BigDecimal getCredit();
    BigDecimal getDebit();
    BigDecimal getBalance();


}
