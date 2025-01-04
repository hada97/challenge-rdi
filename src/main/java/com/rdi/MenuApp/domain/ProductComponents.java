package com.rdi.MenuApp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRODUCT_COMPONENTS")
@Builder
public class ProductComponents {

    public ProductComponents(Long id, Product parentProduct, Product childProduct) {
        this.id = id;
        this.parentProduct = parentProduct;
        this.childProduct = childProduct;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRD_COMP_ID")  // Nome alterado para evitar confusão com PRD_ID
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRD_ID")  // Produto principal (CHOICE ou VALUE MEAL)
    @JsonBackReference // Impede a serialização infinita
    private Product parentProduct;

    @ManyToOne
    @JoinColumn(name = "CHILD_PRD_ID")  // Produto componente (PRODUCT)
    private Product childProduct;

    // Construtor para facilitar a criação do relacionamento
    public ProductComponents(Product parentProduct, Product childProduct) {
        this.parentProduct = parentProduct;
        this.childProduct = childProduct;
    }

    public ProductComponents() {
    }


    public Product getParentProduct() {
        return parentProduct;
    }

    public void setParentProduct(Product parentProduct) {
        this.parentProduct = parentProduct;
    }

    public Product getChildProduct() {
        return childProduct;
    }

    public void setChildProduct(Product childProduct) {
        this.childProduct = childProduct;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
