package com.david.spring_boot_ecommerce_api.dto;

import java.math.BigDecimal;

public class CartItemResponse {

    private Long cartItemId;
    private Long productId;
    private String productName;
    private Integer productQuantity;
    private BigDecimal unityPrice;

    public CartItemResponse(Long cartItemId, String productName, Integer productQuantity, BigDecimal unityPrice, Long productId) {
        this.cartItemId = cartItemId;
        this.productName = productName;
        this.productQuantity = productQuantity;
        this.unityPrice = unityPrice;
        this.productId = productId;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public BigDecimal getUnityPrice() {
        return unityPrice;
    }

    public void setUnityPrice(BigDecimal unityPrice) {
        this.unityPrice = unityPrice;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
