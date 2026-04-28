package com.david.spring_boot_ecommerce_api.controller;

import com.david.spring_boot_ecommerce_api.dto.AddToCartRequest;
import com.david.spring_boot_ecommerce_api.dto.CartItemResponse;
import com.david.spring_boot_ecommerce_api.service.CartService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public CartItemResponse addToCart(@Valid @RequestBody AddToCartRequest request) {
        //recebe o DTO da API e delega lógica pro service
        return cartService.addToCart(request.getProductId(), request.getQuantity());
    }

    @GetMapping
    //list o carrinho do user logado
    public List<CartItemResponse> getCartItems() {
        return cartService.getCartItems();
    }

    @DeleteMapping("/{id}")
    //remove um item do carrinho pelo id
    public void removeCartItem(@PathVariable Long id) {
        cartService.removeCartItem(id);
    }
}
