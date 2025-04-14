package com.iv.iv.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.iv.iv.utility.IvConstants;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "iv_tp_customer_ledger")
public class ToPartiesLedgerEntries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name="tp_customer_id", referencedColumnName = "tp_customer_id", nullable = false)
     private ToParty tpCustomerId;

    public ToParty getTpCustomerId() {
        return tpCustomerId;
    }

    public void setTpCustomerId(ToParty tpCustomerId) {
        this.tpCustomerId = tpCustomerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getParticular() {
        return particular;
    }

    public void setParticular(String particular) {
        this.particular = particular;
    }

    public BigDecimal getDebit() {
        return debit;
    }

    public void setDebit(BigDecimal debit) {
        this.debit = debit;
    }

    public BigDecimal getCredit() {
        return credit;
    }

    public void setCredit(BigDecimal credit) {
        this.credit = credit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Column(name = "date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "IST")
    private LocalDate date;

    @Column(name = "particular")
    private String particular;

    @Column(name = "debit")
    private BigDecimal debit;

    @Column(name = "credit")
    private BigDecimal credit;

    @Column(name = "balance")
    private BigDecimal balance;

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public IvConstants getType() {
        return type;
    }

    public void setType(IvConstants type) {
        this.type = type;
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private IvConstants type;


}
