package com.idv.yen.domain;

public class Product {
    private Integer id;
    private String productName;
    private Integer price;
    private Integer quantity;
    private Integer sellerId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getSellerId() {
        return sellerId;
    }

    public void setSellerId(Integer sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", ProductName='" + productName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", sellerId=" + sellerId +
                '}';
    }
}
