package com.idv.yen.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Order {
    private Integer id;
    private Integer userId;
    private BigDecimal price;
    private Integer payType;
    private Integer paymentStatus;
    private Integer shippingStatus;
    private Timestamp createTime;
    private Timestamp deliveryTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Integer getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(Integer shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(Timestamp deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", userId=" + userId +
                ", price=" + price +
                ", payType=" + payType +
                ", paymentStatus=" + paymentStatus +
                ", shippingStatus=" + shippingStatus +
                ", createTime=" + createTime +
                ", deliveryTime=" + deliveryTime +
                '}';
    }
}
