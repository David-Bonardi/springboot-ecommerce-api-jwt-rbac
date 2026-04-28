package com.david.spring_boot_ecommerce_api.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    //Item pertence a um pedido
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(optional = false)
    //Produto comprado
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(nullable = false)
    //Quantidade comprada
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    //Preço do produto no momento da compra
    private BigDecimal priceAtPurchase;

    public OrderItem(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getPriceAtPurchase() {
        return priceAtPurchase;
    }

    public void setPriceAtPurchase(BigDecimal priceAtPurchase) {
        this.priceAtPurchase = priceAtPurchase;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
