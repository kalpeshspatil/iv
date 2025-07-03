package com.iv.iv.dto;

import java.math.BigDecimal;
import java.util.List;

public class RetailerSalesReportResponseDTO {

    private List<RetailerSalesReportDTO> salesReportList;
    private BigDecimal totalOfSaleBillingAmount;

    public BigDecimal getTotalQuantityOfRetailer() {
        return totalQuantityOfRetailer;
    }

    public List<RetailerSalesReportDTO> getSalesReportList() {
        return salesReportList;
    }

    public void setSalesReportList(List<RetailerSalesReportDTO> salesReportList) {
        this.salesReportList = salesReportList;
    }

    public void setTotalQuantityOfRetailer(BigDecimal totalQuantityOfRetailer) {
        this.totalQuantityOfRetailer = totalQuantityOfRetailer;
    }

    private BigDecimal totalQuantityOfRetailer;


    public BigDecimal getTotalOfSaleBillingAmount() {
        return totalOfSaleBillingAmount;
    }

    public void setTotalOfSaleBillingAmount(BigDecimal totalOfSaleBillingAmount) {
        this.totalOfSaleBillingAmount = totalOfSaleBillingAmount;
    }


}
