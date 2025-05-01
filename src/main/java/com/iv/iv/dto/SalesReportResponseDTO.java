package com.iv.iv.dto;

import java.math.BigDecimal;
import java.util.List;

public class SalesReportResponseDTO {

    private List<SalesReportDTO> salesReportList;
    private BigDecimal totalOfSaleBillingAmount;

    public List<SalesReportDTO> getSalesReportList() {
        return salesReportList;
    }

    public void setSalesReportList(List<SalesReportDTO> salesReportList) {
        this.salesReportList = salesReportList;
    }

    public BigDecimal getTotalOfSaleBillingAmount() {
        return totalOfSaleBillingAmount;
    }

    public void setTotalOfSaleBillingAmount(BigDecimal totalOfSaleBillingAmount) {
        this.totalOfSaleBillingAmount = totalOfSaleBillingAmount;
    }


}
