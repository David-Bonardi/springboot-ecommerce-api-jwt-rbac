package com.david.spring_boot_ecommerce_api.repository;

import com.david.spring_boot_ecommerce_api.model.CartItem;
import com.david.spring_boot_ecommerce_api.model.Product;
import com.david.spring_boot_ecommerce_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// Vai ser usado para:
// listar itens do carrinho de um usuário
// encontrar um item específico daquele usuário e produto
// remover itens depois do checkout
public interface CartItemRespository extends JpaRepository<CartItem, Long> {

    //Busca todos os itens do carrinho de um usuário
    List<CartItem> findByUser(User user);

    //busca um item especifico no carrinho
    Optional<CartItem> findByUserAndProduct(User user, Product product);

    //Remove todos os itens do carrinho de um usuário
    void deleteByUser(User user);
}
