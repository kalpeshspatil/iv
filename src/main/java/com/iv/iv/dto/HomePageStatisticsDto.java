package com.iv.iv.dto;

import java.math.BigDecimal;
import java.util.List;

public class HomePageStatisticsDto {
    private BigDecimal a1Outstanding;

    private BigDecimal accOutstanding;

    private BigDecimal shaktiOutstanding;


    private int noOfDuePayments;

    private BigDecimal totalSaleOfAcc;

    private BigDecimal totalSaleOfA1;

    private BigDecimal totalSaleOfShakti;

    private OutstandingGroupByDaysDTO outstandingGroupByDaysDTO;

    public OutstandingGroupByDaysDTO getOutstandingGroupByDaysDTO() {
        return outstandingGroupByDaysDTO;
    }

    public void setOutstandingGroupByDaysDTO(OutstandingGroupByDaysDTO outstandingGroupByDaysDTO) {
        this.outstandingGroupByDaysDTO = outstandingGroupByDaysDTO;
    }

    public int getNoOfDuePayments() {
        return noOfDuePayments;
    }

    public BigDecimal getTotalSaleOfAcc() {
        return totalSaleOfAcc;
    }

    public void setTotalSaleOfAcc(BigDecimal totalSaleOfAcc) {
        this.totalSaleOfAcc = totalSaleOfAcc;
    }

    public BigDecimal getTotalSaleOfA1() {
        return totalSaleOfA1;
    }

    public void setTotalSaleOfA1(BigDecimal totalSaleOfA1) {
        this.totalSaleOfA1 = totalSaleOfA1;
    }

    public BigDecimal getTotalSaleOfShakti() {
        return totalSaleOfShakti;
    }

    public void setTotalSaleOfShakti(BigDecimal totalSaleOfShakti) {
        this.totalSaleOfShakti = totalSaleOfShakti;
    }

    public void setNoOfDuePayments(int noOfDuePayments) {
        this.noOfDuePayments = noOfDuePayments;
    }

    public BigDecimal getA1Outstanding() {
        return a1Outstanding;
    }

    public void setA1Outstanding(BigDecimal a1Outstanding) {
        this.a1Outstanding = a1Outstanding;
    }

    public BigDecimal getAccOutstanding() {
        return accOutstanding;
    }

    public void setAccOutstanding(BigDecimal accOutstanding) {
        this.accOutstanding = accOutstanding;
    }

    public BigDecimal getShaktiOutstanding() {
        return shaktiOutstanding;
    }

    public void setShaktiOutstanding(BigDecimal shaktiOutstanding) {
        this.shaktiOutstanding = shaktiOutstanding;
    }
}
