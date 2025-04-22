package com.iv.iv.dto;

import java.math.BigDecimal;

public class ProductTotalQuantityDTO {
    private String productBrand;
    private String productName;
    private BigDecimal totalQuantity;

    public ProductTotalQuantityDTO(String productBrand, String productName, BigDecimal totalQuantity) {
        this.productBrand = productBrand;
        this.productName = productName;
        this.totalQuantity = totalQuantity;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
// Getters & Setters
}
