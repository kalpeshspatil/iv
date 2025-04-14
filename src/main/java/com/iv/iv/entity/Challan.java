package com.iv.iv.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

@Entity
@Table(name = "iv_challan")
public class Challan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challan_id",  nullable = false)
    private Long challanId;

    @OneToOne
    @JoinColumn(name="product_id", referencedColumnName = "product_id")
    private Product product;

    @OneToOne
    @JoinColumn(name="pf_customer_id", referencedColumnName = "pf_customer_id")
    private PurchaseFrom purchaseFrom;

    @OneToMany(mappedBy = "challan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChallanToParties> challanToParties = new ArrayList<>();



    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "IST")
    @Column(name = "order_delivery_date")
    private LocalDate orderDeliveryDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "IST")
    @Column(name = "order_placed_date", updatable = false)
    private LocalDate orderPlacedDate;

    @Column(name = "vehicle_number")
    private String vehicleNumber;

    @Column(name = "pfRate")
    private Double purchaseFromRate;

    @Column(name = "pfQuantity")
    private int purchaseFromQuantity;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name="challan_status")
    private String challanStatus;

    @PrePersist
    protected void onCreate() {
        this.challanStatus = "PENDING";
    }

    public Long getChallanId() {
        return challanId;
    }

    public void setChallanId(Long challanId) {
        this.challanId = challanId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public PurchaseFrom getPurchaseFrom() {
        return purchaseFrom;
    }

    public void setPurchaseFrom(PurchaseFrom purchaseFrom) {
        this.purchaseFrom = purchaseFrom;
    }

    public List<ChallanToParties> getChallanToParties() {
        return challanToParties;
    }

    public void setChallanToParties(List<ChallanToParties> challanToParties) {
        this.challanToParties = challanToParties;
    }

    public LocalDate getOrderDeliveryDate() {
        return orderDeliveryDate;
    }

    public void setOrderDeliveryDate(LocalDate orderDeliveryDate) {
        this.orderDeliveryDate = orderDeliveryDate;
    }

    public LocalDate getOrderPlacedDate() {
        return orderPlacedDate;
    }

    public void setOrderPlacedDate(LocalDate orderPlacedDate) {
        this.orderPlacedDate = orderPlacedDate;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public Double getPurchaseFromRate() {
        return purchaseFromRate;
    }

    public void setPurchaseFromRate(Double purchaseFromRate) {
        this.purchaseFromRate = purchaseFromRate;
    }

    public int getPurchaseFromQuantity() {
        return purchaseFromQuantity;
    }

    public void setPurchaseFromQuantity(int purchaseFromQuantity) {
        this.purchaseFromQuantity = purchaseFromQuantity;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public String getChallanStatus() {
        return challanStatus;
    }

    public void setChallanStatus(String challanStatus) {
        this.challanStatus = challanStatus;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}
