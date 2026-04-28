package com.david.spring_boot_ecommerce_api.service;

import com.david.spring_boot_ecommerce_api.model.Product;
import com.david.spring_boot_ecommerce_api.repository.ProductRespository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRespository productRespository;

    public ProductService(ProductRespository productRespository) {
        this.productRespository = productRespository;
    }

    public Product create(Product product) {
        return productRespository.save(product);
    }

    public List<Product> getAll() {
        return productRespository.findAll();
    }

    public Product getById(Long id) {
        return productRespository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));
    }

    public Product update(Long id, Product updatedProduct) {
        Product product = getById(id);

        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        product.setStock(updatedProduct.getStock());

        return productRespository.save(product);
    }

    public void delete(Long id){
        productRespository.deleteById(id);
    }
}
