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
    @JoinColumn(name="tp_customer_id", referencedColumnName = "tp_customer_id", unique = false)
    private ToParty toParty;

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

    public ToParty getToParty() {
        return toParty;
    }

    public void setToParty(ToParty toParty) {
        this.toParty = toParty;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }
}

