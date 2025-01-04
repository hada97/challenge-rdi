package com.rdi.MenuApp.controller;

import com.rdi.MenuApp.domain.Product;
import com.rdi.MenuApp.domain.ProductStatus;
import com.rdi.MenuApp.DTO.ProductRequestDTO;
import com.rdi.MenuApp.exception.ProductNotFoundException;
import com.rdi.MenuApp.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Object> getAllProducts() {
        List<Product> products = productService.findAll();
        if (products.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "No products found"));
        }
        return ResponseEntity.ok(products);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Long id) {
        Product product = productService.findById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.status(404).body(null);
    }


    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequestDTO productRequestDTO) {

        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setType(productRequestDTO.getType());

        // Cria e associa o status
        ProductStatus productStatus = new ProductStatus();
        productStatus.setStatus(productRequestDTO.getStatus().getStatus()); // Define o status do produto
        productStatus.setProduct(product); // Associa o produto ao status
        product.setStatus(productStatus); // Define o status do produto

        // Processa os componentes
        product.setComponents(productRequestDTO.getComponents());

        // Salva o produto
        Product savedProduct = productService.saveProduct(product);

        // Retorna a resposta
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        boolean isDeleted = productService.deleteProduct(id);
        if (isDeleted) {
            return ResponseEntity.ok("Product deleted successfully!");
        }
        return ResponseEntity.status(404).body("Product not found!");
    }
}
