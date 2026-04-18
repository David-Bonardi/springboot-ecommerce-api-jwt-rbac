package com.david.spring_boot_ecommerce_api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class AddToCartRequest {

    @NotNull(message = "O Id do produto é obrigatório!")
    private Long productId;

    @NotNull(message = "Quantidade é obrigatória!")
    @Positive(message = "Quantidade deve ser maior que zero!")
    private Integer quantity;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
