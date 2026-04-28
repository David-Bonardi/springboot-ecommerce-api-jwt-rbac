package com.david.spring_boot_ecommerce_api.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    // ID gerado automaticamente
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Nome do produto é obrigatório
    @Column(nullable = false)
    private String name;

    // Descrição opcional, mas com limite maior de texto
    @Column(length = 1000)
    private String description;

    // precision = quantidade total de dígitos
    // scale = quantidade de casas decimais
    // Ex.: 99999999.99
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    // Quantidade disponível em estoque
    @Column(nullable = false)
    private Integer stock;

    public Product() {

    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
