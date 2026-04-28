package com.david.spring_boot_ecommerce_api.repository;

import com.david.spring_boot_ecommerce_api.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRespository extends JpaRepository<Product, Long> {
}
