package com.iv.iv.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface SalesReportDTO {

    LocalDate getSaleDate();
    String getCustomerName();
    String getQuantity();
    BigDecimal getSaleBillingRate();
    BigDecimal getPurchaseBillingRate();
    BigDecimal getTotalAmount();
}

