package com.iv.iv.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "iv_to_parties_of_challan")
public class ChallanToParties {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="challan_tp_pkid", nullable = false)
    private Long pkId;

    @JsonIgnoreProperties({"challanToParties"}) // This will ignore the "challan" field inside ChallanToParties
    @ManyToOne
    @JoinColumn(name = "challan_id", nullable = false)
    private Challan challan;  // Reference to Challan

    @ManyToOne
    @JoinColumn(name="tp_customer_id", referencedColumnName = "tp_customer_id", nullable = false)
    private ToParty selectedToParty;

    @Column(name = "challan_to_parties_qty")
    private int challanToPartiesQty;

    @Column(name = "outstanding_payment")
    private BigDecimal outstandingPayment;

    public BigDecimal getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(BigDecimal grossAmount) {
        this.grossAmount = grossAmount;
    }

    @Column(name = "gross_amount")
    private BigDecimal grossAmount;

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Long getPkId() {
        return pkId;
    }

    public BigDecimal getOutstandingPayment() {
        return outstandingPayment;
    }

    public void setOutstandingPayment(BigDecimal outstandingPayment) {
        this.outstandingPayment = outstandingPayment;
    }

    public void setPkId(Long pkId) {
        this.pkId = pkId;
    }

    public Challan getChallan() {
        return challan;
    }

    public void setChallan(Challan challan) {
        this.challan = challan;
    }

    public ToParty getSelectedToParty() {
        return selectedToParty;
    }

    public void setSelectedToParty(ToParty selectedToParty) {
        this.selectedToParty = selectedToParty;
    }

    public int getChallanToPartiesQty() {
        return challanToPartiesQty;
    }

    public void setChallanToPartiesQty(int challanToPartiesQty) {
        this.challanToPartiesQty = challanToPartiesQty;
    }

    public BigDecimal getChallanToPartiesRate() {
        return challanToPartiesRate;
    }

    public void setChallanToPartiesRate(BigDecimal challanToPartiesRate) {
        this.challanToPartiesRate = challanToPartiesRate;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Column(name = "challan_to_parties_rate")
    private BigDecimal challanToPartiesRate;

    @Column(name = "order_status", columnDefinition = "VARCHAR(255) default 'PENDING'")
    private String orderStatus;

    @Column(name = "payment_status", columnDefinition = "VARCHAR(255) default 'PENDING'")
    private String paymentStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "IST")
    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    @PrePersist
    protected void onCreate() {
        if (orderStatus == null) {
            orderStatus = "PENDING";
            paymentStatus = "PENDING";
        }
    }


}
