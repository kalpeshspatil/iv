package com.iv.iv.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "iv_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_brand")
    private String productBrand;

    public Double getProductLandingPrice() {
        return productLandingPrice;
    }

    public void setProductLandingPrice(Double productLandingPrice) {
        this.productLandingPrice = productLandingPrice;
    }

    @Column(name = "product_landing_price")
    private Double productLandingPrice;


}