package com.iv.iv.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "to_parties_payments")
public class ToPartiesPayments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_pkid")
    private Long paymentPkid;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "IST")
    @Column(name = "payment_date")
    private String paymentDate;

    @ManyToOne
    @JoinColumn(name="challan_tp_pkid", referencedColumnName = "challan_tp_pkid", unique = false)
    private ChallanToParties challanToParties;

    @Column(name = "payment")
    private double payment;

    public Long getPaymentPkid() {
        return paymentPkid;
    }

    public void setPaymentPkid(Long paymentPkid) {
        this.paymentPkid = paymentPkid;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }



    public double getPayment() {
        return payment;
    }

    public ChallanToParties getChallanToParties() {
        return challanToParties;
    }

    public void setChallanToParties(ChallanToParties challanToParties) {
        this.challanToParties = challanToParties;
    }

    public void setPayment(double payment) {
        this.payment = payment;
    }
}

