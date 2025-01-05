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

    @Transactional
    public void changeStatus(Long menuItemId, int status) throws ProductNotFoundException {

        if (status != 0 && status != 1) {
            throw new IllegalArgumentException("Invalid status value. It should be 0 or 1.");
        }

        Product product = productRepository.findById(menuItemId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        // Verifica o tipo do produto e se a alteração de status é permitida
        boolean isStatusChangeAllowed = false;

        switch (product.getType()) {
            case 1: // Tipo 1
                isStatusChangeAllowed = true;
                break;
            case 2: // Tipo CHOICE
                isStatusChangeAllowed = hasActiveComponent(menuItemId, status);
                break;
            case 3: // Tipo VALUE MEAL
                System.out.println("VALUE MEAL");
                isStatusChangeAllowed = hasAllComponentsActive(menuItemId, status);
                break;
        }

        if (isStatusChangeAllowed) {
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

    public boolean hasAllComponentsActive(Long menuItemId, int status) {
        List<Long> componentIds = productComponentsRepository.findByParentId(menuItemId);
        // Verifica se algum componente está inativo diretamente
        return componentIds.stream()
                .allMatch(componentId -> productStatusRepository.findStatusByProductId(componentId) != 0);
    }



    @Transactional
    public void updateProductStatus(Long menuItemId, int status) {

        // Recupera o produto
        Product product = productRepository.findById(menuItemId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        System.out.println(product.getName());

        // Verifica se o ProductStatus está presente
        ProductStatus prod = product.getStatus();

        if (prod == null) {

            prod = new ProductStatus();
            prod.setStatus(status);  // Define o novo status
            prod.setProduct(product); // Associa o Product ao ProductStatus

            // Salva o novo ProductStatus no banco
            productStatusRepository.save(prod);

            // Associa o ProductStatus ao Product
            product.setStatus(prod);
        } else {
            // Atualiza o status do ProductStatus existente
            prod.setStatus(status);
            productStatusRepository.save(prod); // Salva a atualização do ProductStatus
        }

        // Atualiza o Product
        productRepository.save(product); // Salva o Product com o novo status
        System.out.println("Status do produto atualizado: " + prod.getStatus());
    }



}
