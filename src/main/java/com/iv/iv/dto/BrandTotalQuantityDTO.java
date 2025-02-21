package com.iv.iv.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class BrandTotalQuantityDTO {
  //  @JsonProperty("product_brand")
    private String brand;
   // @JsonProperty("total_quantity")
    private BigDecimal totalQuantity;

    private BigDecimal outstanding;

    // Constructor, getters, and setters
    public BrandTotalQuantityDTO(String brand, BigDecimal totalQuantity, BigDecimal outstanding) {
        this.brand = brand;
        this.totalQuantity = totalQuantity;
        this.outstanding = outstanding;
    }

    public BigDecimal getOutstanding() {
        return outstanding;
    }

    public void setOutstanding(BigDecimal outstanding) {
        this.outstanding = outstanding;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
}
