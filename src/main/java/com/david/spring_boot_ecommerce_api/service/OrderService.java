package com.david.spring_boot_ecommerce_api.service;

import com.david.spring_boot_ecommerce_api.dto.OrderItemResponse;
import com.david.spring_boot_ecommerce_api.dto.OrderResponse;
import com.david.spring_boot_ecommerce_api.model.*;
import com.david.spring_boot_ecommerce_api.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {
    private final CartItemRespository cartItemRespository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final ProductRespository productRespository;

    public OrderService(CartItemRespository cartItemRespository, OrderRepository orderRepository, OrderItemRepository orderItemRepository, UserRepository userRepository, ProductRespository productRespository) {
        this.cartItemRespository = cartItemRespository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.userRepository = userRepository;
        this.productRespository = productRespository;
    }

    @Transactional
    public OrderResponse checkout() {
        //pega o user logado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        //busca item no carrinho
        List<CartItem> cartItems = cartItemRespository.findByUser(user);

        if (cartItems.isEmpty()) {
            throw new RuntimeException("Carrinho vazio!");
        }

        //cria pedido
        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());

        //calcula total
        BigDecimal total = BigDecimal.ZERO;
        for (CartItem cartItem: cartItems){
            BigDecimal itemTotal = cartItem.getProduct()
                    .getPrice()
                    .multiply(BigDecimal.valueOf(cartItem.getQuantity()));
                    total = total.add(itemTotal);
        }

        order.setTotal(total);

        //salva o pedido
        Order savedOrder = orderRepository.save(order);

        //cria order items
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();

            //valida estoque novamente
            if (cartItem.getQuantity() > product.getStock()){
                throw new RuntimeException("Estoque insuficiente para o produto: " + product.getName());
            }

            //diminui estoque
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRespository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(savedOrder);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPriceAtPurchase(product.getPrice());

            orderItemRepository.save(orderItem);
        }

        //limpa o carrinho
        cartItemRespository.deleteByUser(user);

        //retorna resposta
        return buildOrderResponse(savedOrder);
    }

    public List<OrderResponse> getUserOrders() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        return orderRepository.findByUser(user).stream()
                .map(this::buildOrderResponse)
                .toList();
    }

    private OrderResponse buildOrderResponse(Order order) {
        List<OrderItem> items = orderItemRepository.findByOrder(order);

        List<OrderItemResponse> itemResponses = items.stream()
                .map(item -> new OrderItemResponse(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPriceAtPurchase()
                ))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getTotal(),
                order.getStatus(),
                order.getCreatedAt(),
                itemResponses
        );
    }
}
