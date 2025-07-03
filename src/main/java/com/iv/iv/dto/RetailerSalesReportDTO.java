package com.iv.iv.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface RetailerSalesReportDTO {
    LocalDate getSaleDate();
    String getTpCustomerName();
    String getRetailerName();
    String getQuantity();
    BigDecimal getSaleBillingRate();
    BigDecimal getPurchaseBillingRate();
    BigDecimal getTotalAmount();
}

