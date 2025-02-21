package com.iv.iv.dto;

import com.iv.iv.entity.ChallanToParties;

public class ChallanToPartiesDto {
    private int pkId;
    private int challanToPartiesQty;
    private String orderStatus;
    private Long challanId;  // Avoid fetching full Challan object

    public ChallanToPartiesDto(ChallanToParties challanToParties) {
        this.pkId = challanToParties.getPkId();
        this.challanToPartiesQty = challanToParties.getChallanToPartiesQty();
        this.orderStatus = challanToParties.getOrderStatus();
        this.challanId = challanToParties.getChallan().getChallanId();
    }

    public int getPkId() {
        return pkId;
    }

    public void setPkId(int pkId) {
        this.pkId = pkId;
    }

    public int getChallanToPartiesQty() {
        return challanToPartiesQty;
    }

    public void setChallanToPartiesQty(int challanToPartiesQty) {
        this.challanToPartiesQty = challanToPartiesQty;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Long getChallanId() {
        return challanId;
    }

    public void setChallanId(Long challanId) {
        this.challanId = challanId;
    }
// Getters...
}

