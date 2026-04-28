package com.david.spring_boot_ecommerce_api.repository;

import com.david.spring_boot_ecommerce_api.model.Order;
import com.david.spring_boot_ecommerce_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//vai ser usado para listar os pedidos de um usuario logado
public interface OrderRepository extends JpaRepository<Order, Long> {

    //busca todos os orders de um user
    List<Order> findByUser(User user);
}
