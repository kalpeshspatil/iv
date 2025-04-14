package com.iv.iv.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;


import java.math.BigDecimal;
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

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "IST")
    @Column(name = "payment_date")
    private LocalDate paymentDate;

    @ManyToOne
    @JoinColumn(name="challan_tp_pkid", referencedColumnName = "challan_tp_pkid", unique = false)
    private ChallanToParties challanToParties;

    @Column(name = "payment")
    private BigDecimal payment;

    public Long getPaymentPkid() {
        return paymentPkid;
    }

    public void setPaymentPkid(Long paymentPkid) {
        this.paymentPkid = paymentPkid;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }



    public BigDecimal getPayment() {
        return payment;
    }

    public ChallanToParties getChallanToParties() {
        return challanToParties;
    }

    public void setChallanToParties(ChallanToParties challanToParties) {
        this.challanToParties = challanToParties;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }
}

