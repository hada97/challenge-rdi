package com.rdi.MenuApp.service;

import com.rdi.MenuApp.DTO.ProductRequestDTO;
import com.rdi.MenuApp.domain.Product;
import com.rdi.MenuApp.domain.ProductComponents;
import com.rdi.MenuApp.domain.ProductStatus;
import com.rdi.MenuApp.repository.ProductComponentsRepository;
import com.rdi.MenuApp.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductComponentsRepository productComponentsRepository;


    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public Product findById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public Product createProduct(ProductRequestDTO productRequestDTO) {

        Product product = new Product();
        product.setName(productRequestDTO.getName());
        product.setType(productRequestDTO.getType());

        ProductStatus status = new ProductStatus();
        status.setStatus(productRequestDTO.getStatus().getStatus());  // Supondo que ProductStatusRequestDTO tenha um campo "status"
        product.setStatus(status);

        // Salvando o produto e o status
        product = productRepository.save(product);

        // Condicional para garantir que, se o tipo for 3 (VALUE MEAL), os componentes sejam obrigatórios
        if (productRequestDTO.getType() == 3) {
            if (productRequestDTO.getComponentIds() == null || productRequestDTO.getComponentIds().isEmpty()) {
                throw new RuntimeException("Os componentes são obrigatórios para um VALUE MEAL.");
            }
            // Associando os componentes ao produto
            for (Long componentId : productRequestDTO.getComponentIds()) {
                Product componentProduct = productRepository.findById(componentId).orElseThrow(() -> new RuntimeException("Componente não encontrado"));
                // Criando um ProductComponents para cada componente
                ProductComponents productComponents = new ProductComponents();
                productComponents.setParentProduct(product);  // O produto pai (CHOICE ou VALUE MEAL)
                productComponents.setChildProduct(componentProduct);  // O produto componente (PRODUCT)
                // Salvando a relação entre o produto e seu componente
                productComponentsRepository.save(productComponents);
            }
        }
        return product;
    }

    @Transactional
    public boolean deleteProduct(Long productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            productRepository.deleteById(productId);
            return true;
        }
        return false;
    }
}
