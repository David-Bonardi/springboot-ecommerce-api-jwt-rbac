package com.david.spring_boot_ecommerce_api.service;

import com.david.spring_boot_ecommerce_api.dto.CartItemResponse;
import com.david.spring_boot_ecommerce_api.model.CartItem;
import com.david.spring_boot_ecommerce_api.model.Product;
import com.david.spring_boot_ecommerce_api.model.User;
import com.david.spring_boot_ecommerce_api.repository.CartItemRespository;
import com.david.spring_boot_ecommerce_api.repository.ProductRespository;
import com.david.spring_boot_ecommerce_api.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartItemRespository cartItemRespository;
    private final ProductRespository productRespository;
    private final UserRepository userRepository;

    public CartService(CartItemRespository cartItemRespository, ProductRespository productRespository, UserRepository userRepository) {
        this.cartItemRespository = cartItemRespository;
        this.productRespository = productRespository;
        this.userRepository = userRepository;
    }

    public CartItemResponse addToCart(Long productId, Integer quantity){

        //Pega o user logado na request atual
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        //Pega o usuário no banco pelo username extraído no token
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));

        //Busca o produto que o usuário quer adicionar
        Product product = productRespository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));

        //valida se quatidade solicitada existe em estoque
        if (quantity > product.getStock()) {
            throw new RuntimeException("Quantidade solicitada é maior que o estoque disponível!");
        }

        //verifica se esse user ja tem esse produto no carrinho
        CartItem cartItem = cartItemRespository.findByUserAndProduct(user, product)
                //se não tiver, cria um item novo
                .orElseGet(() -> {
                    CartItem newItem = new CartItem();
                    newItem.setUser(user);
                    newItem.setProduct(product);
                    newItem.setQuantity(0);
                    return newItem;
                });

        //Soma a quantidade atual com a nova quantidade
        int newQuantity = cartItem.getQuantity() + quantity;

        //valida novamente para garantir que a soma não passe do estoque
        if (newQuantity > product.getStock()) {
            throw new RuntimeException("Quantidade no carrinho excede o estoque disponível para este produto!");
        }

        cartItem.setQuantity(newQuantity);

        //salva no banco
        CartItem savedItem = cartItemRespository.save(cartItem);

        return toResponse(savedItem);
    }

    public List<CartItemResponse> getCartItems() {
        //pega o user logado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        //busca o user no banco
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        //pega todos os itens no carrinho desse usuário
        return cartItemRespository.findByUser(user).stream()
                .map(this::toResponse)
                .toList();
    }

    public void removeCartItem(Long cartItemId) {
        //pega o user logado
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        //pega o user no banco
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        //busca o item no carrinho pelo id
        CartItem cartItem = cartItemRespository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Item do carrinho não encontrado!"));

        //garante que o item pertence ao user logado
        if (!cartItem.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Você não pode remover um item de outro usuário!");
        }

        //remove o item
        cartItemRespository.delete(cartItem);


    }

    private CartItemResponse toResponse(CartItem cartItem) {
        return new CartItemResponse(
                cartItem.getId(),
                cartItem.getProduct().getId(),
                cartItem.getProduct().getName(),
                cartItem.getQuantity(),
                cartItem.getProduct().getPrice()
        );
    }
}
