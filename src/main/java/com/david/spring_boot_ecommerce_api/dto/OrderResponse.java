package com.david.spring_boot_ecommerce_api.dto;

import com.david.spring_boot_ecommerce_api.model.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class OrderResponse {

    private Long orderId;
    private BigDecimal total;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;

    public OrderResponse(Long orderId, List<OrderItemResponse> items, LocalDateTime createdAt, OrderStatus status, BigDecimal total) {
        this.orderId = orderId;
        this.items = items;
        this.createdAt = createdAt;
        this.status = status;
        this.total = total;
    }

    public Long getOrderId() {
        return orderId;
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
