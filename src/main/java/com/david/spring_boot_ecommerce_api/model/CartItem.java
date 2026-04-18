package com.david.spring_boot_ecommerce_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cart_items")
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    //Cada item no carrinho pertence a um usuário
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    //Cada item no carrinho aponta para um produto
    @JoinColumn(name = "product_id")
    private Product product;

    //Quantidade daquele produto no carrinho
    @Column(nullable = false)
    private Integer quantity;

    public CartItem(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
