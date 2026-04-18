package com.david.spring_boot_ecommerce_api.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    //Pedido pertence a um usuario
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, precision = 10, scale = 2)
    //Total do pedido
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    //Status do pedido(CREATED, CANCELLED)
    private OrderStatus status;

    @Column(nullable = false)
    //Data da criação do pedido
    private LocalDateTime createdAt;

    public Order() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
