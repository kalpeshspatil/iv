package com.iv.iv.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public interface ToPartyLedgerDto {
    Long getTpCustomerId();
    String getCustomerName();
    BigDecimal getCredit();
    BigDecimal getDebit();
    BigDecimal getBalance();


}
