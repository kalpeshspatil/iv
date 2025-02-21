package com.iv.iv.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "iv_to_party")
public class ToParty {

    public Long getTpCustomerId() {
        return tpCustomerId;
    }

    public void setTpCustomerId(Long tpCustomerId) {
        this.tpCustomerId = tpCustomerId;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tp_customer_id", unique = true, nullable = false)
    private Long tpCustomerId;

    @Column(name = "tp_customer_name")
    private String customerName;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
}
