package com.rdi.MenuApp.service;

import com.rdi.MenuApp.domain.Product;
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

    public void changeStatus(Long menuItemId, int status) throws ProductNotFoundException {

        if (status != 0 && status != 1) {
            throw new IllegalArgumentException("Invalid status value. It should be 0 or 1.");
        }

        Product product = productRepository.findById(menuItemId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));

        if (product.getType() == 2) { // Tipo CHOICE
            if (hasActiveComponent(menuItemId, status)) {
                updateProductStatus(menuItemId, status);
            }
        } else if (product.getType() == 3) { // Tipo VALUE MEAL
            if (hasAllComponentsActive(menuItemId, status)) {
                updateProductStatus(menuItemId, status);
            }
        } else {
            updateProductStatus(menuItemId, status);
        }
    }

    //CHOICE
    public boolean hasActiveComponent(Long menuItemId, int status) {
        List<Long> componentIds = productComponentsRepository.findByParentId(menuItemId);
        for (Long componentId : componentIds) {
            int componentStatus = productStatusRepository.findStatusByProductId(componentId);
            if (componentStatus == 1) {
                return true;
            }
        }
        return false;
    }

    // VALUE MEAL
    public boolean hasAllComponentsActive(Long menuItemId, int status) {
        List<Long> componentIds = productComponentsRepository.findByParentId(menuItemId);
        for (Long componentId : componentIds) {
            int componentStatus = productStatusRepository.findStatusByProductId(componentId);
            if (componentStatus == 0) {
                return false;
            }
        }
        return true;
    }

    // Product
    @Transactional
    public void updateProductStatus(Long menuItemId, int status) {
        productStatusRepository.updateStatus(menuItemId, status);
    }
}
