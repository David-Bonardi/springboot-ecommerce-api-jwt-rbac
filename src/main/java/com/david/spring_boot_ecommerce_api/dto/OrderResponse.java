package com.david.spring_boot_ecommerce_api.dto;

import com.david.spring_boot_ecommerce_api.model.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal total;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;

    public OrderResponse(Long id, BigDecimal total, OrderStatus status, LocalDateTime createdAt, List<OrderItemResponse> items) {
        this.id = id;
        this.total = total;
        this.status = status;
        this.createdAt = createdAt;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public List<OrderItemResponse> getItems() {
        return items;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public BigDecimal getTotal() {
        return total;
    }
}
