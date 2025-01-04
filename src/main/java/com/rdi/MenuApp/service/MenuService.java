package com.rdi.MenuApp.service;

import com.rdi.MenuApp.domain.Product;
import com.rdi.MenuApp.domain.ProductStatus;
import com.rdi.MenuApp.repository.ProductComponentsRepository;
import com.rdi.MenuApp.repository.ProductRepository;
import com.rdi.MenuApp.repository.ProductStatusRepository;
import com.rdi.MenuApp.exception.ProductNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MenuService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductStatusRepository productStatusRepository;

    @Autowired
    private ProductComponentsRepository productComponentsRepository;

    // Método que altera o status do produto
    public void changeStatus(Long menuItemId, int status) throws ProductNotFoundException {
        // Verifica se o status é válido (0 ou 1)
        if (status != 0 && status != 1) {
            throw new IllegalArgumentException("Invalid status value. It should be 0 or 1.");
        }

        // Busca o produto
        Product product = productRepository.findById(menuItemId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        // Verifica o tipo do produto e se a alteração de status é permitida
        if (product.getType() == 2) { // Tipo CHOICE
            if (hasActiveComponent(menuItemId, status)) {
                updateProductStatus(menuItemId, status);
            }
        } else if (product.getType() == 3) { // Tipo VALUE MEAL
            if (hasAllComponentsActive(menuItemId, status)) {
                updateProductStatus(menuItemId, status);
            }
        } else { // Outros tipos de produtos
            updateProductStatus(menuItemId, status);
        }
    }

    // Verifica se algum componente ativo de um produto do tipo CHOICE possui status 1
    public boolean hasActiveComponent(Long menuItemId, int status) {
        List<Long> componentIds = productComponentsRepository.findByParentId(menuItemId);
        for (Long componentId : componentIds) {
            int componentStatus = productStatusRepository.findStatusByProductId(componentId);
            if (componentStatus == 1) {
                return true; // Encontrou um componente ativo
            }
        }
        return false; // Nenhum componente ativo
    }

    // Verifica se todos os componentes de um produto do tipo VALUE MEAL estão ativos
    public boolean hasAllComponentsActive(Long menuItemId, int status) {
        List<Long> componentIds = productComponentsRepository.findByParentId(menuItemId);
        for (Long componentId : componentIds) {
            int componentStatus = productStatusRepository.findStatusByProductId(componentId);
            if (componentStatus == 0) {
                return false; // Encontrou um componente inativo
            }
        }
        return true; // Todos os componentes estão ativos
    }

    // Atualiza o status do produto
    @Transactional
    public void updateProductStatus(Long menuItemId, int status) {
        // Busca o produto diretamente e usa seu status
        Product product = productRepository.findById(menuItemId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        ProductStatus productStatus = product.getStatus(); // Acessa o status do produto

        // Atualiza o status do produto
        productStatus.setStatus(status);
        productStatusRepository.save(productStatus); // Salva a alteração no status do produto

        // Salva o produto (necessário para garantir a consistência)
        productRepository.save(product);
    }
}
