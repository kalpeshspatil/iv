package com.iv.iv.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "iv_customer")
public class PurchaseFrom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pf_customer_id")
    private Long pfCustomerId;

    public String getPfCustomerName() {
        return pfCustomerName;
    }

    public void setPfCustomerName(String pfCustomerName) {
        this.pfCustomerName = pfCustomerName;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Column(name = "pf_customer_name")
    private String pfCustomerName;

    public Long getPfCustomerId() {
        return pfCustomerId;
    }

    public void setPfCustomerId(Long pfCustomerId) {
        this.pfCustomerId = pfCustomerId;
    }

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
}
