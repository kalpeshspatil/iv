package com.iv.iv.dto;

import java.math.BigDecimal;
import java.util.List;

public class SalesReportResponseDTO {

    private List<SalesReportDTO> salesReportList;
    private BigDecimal totalOfSaleBillingAmount;

    public BigDecimal getTotalQuantityOfRetailer() {
        return totalQuantityOfRetailer;
    }

    public void setTotalQuantityOfRetailer(BigDecimal totalQuantityOfRetailer) {
        this.totalQuantityOfRetailer = totalQuantityOfRetailer;
    }

    private BigDecimal totalQuantityOfRetailer;

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
