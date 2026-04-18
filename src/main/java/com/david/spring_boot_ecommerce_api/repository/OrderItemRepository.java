package com.david.spring_boot_ecommerce_api.repository;

import com.david.spring_boot_ecommerce_api.model.Order;
import com.david.spring_boot_ecommerce_api.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

//vai ser usado para buscar os itens que pertencem a um pedido especifico
public interface OrderItemRepository extends JpaRepository<Order, Long> {

    //busca todos os itens de um pedido
    List<OrderItem> findByOrder(Order order);
}
