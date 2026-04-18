package com.david.spring_boot_ecommerce_api.controller;

import com.david.spring_boot_ecommerce_api.dto.CreateProductRequest;
import com.david.spring_boot_ecommerce_api.dto.ProductResponse;
import com.david.spring_boot_ecommerce_api.model.Product;
import com.david.spring_boot_ecommerce_api.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    //Só ADMIN pode criar produtos
    public ProductResponse create(@Valid @RequestBody CreateProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product saved = productService.create(product);

        return toResponse(saved);

    }

    @GetMapping
    public List<ProductResponse> getAll(){
        return productService.getAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductResponse getById(@PathVariable Long id) {
        return toResponse(productService.getById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    //Só ADMIN pode atualizar produtos
    public ProductResponse update(@PathVariable Long id, @Valid @RequestBody CreateProductRequest request){
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        return toResponse(productService.update(id, product));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    //Só ADMIN pode deletar produtos
    public void delete(@PathVariable Long id){
        productService.delete(id);
    }

    private ProductResponse toResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock()
        );
    }
}
