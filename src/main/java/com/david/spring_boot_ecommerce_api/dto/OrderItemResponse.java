package com.david.spring_boot_ecommerce_api.dto;

import java.math.BigDecimal;

public class OrderItemResponse {

    private Long productId;
    private String productName;
    private Integer productQuantity;
    private BigDecimal priceAtPurchase;

    public OrderItemResponse(Long productId, BigDecimal priceAtPurchase, Integer productQuantity, String productName) {
        this.productId = productId;
        this.priceAtPurchase = priceAtPurchase;
        this.productQuantity = productQuantity;
        this.productName = productName;
    }

    public Long getProductId() {
        return productId;
    }

    public BigDecimal getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public String getProductName() {
        return productName;
    }
}
