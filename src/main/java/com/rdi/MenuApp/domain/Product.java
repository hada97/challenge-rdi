package com.rdi.MenuApp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "PRODUCT")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Product {

    public Product() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRD_ID")
    private Long id;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public List<ProductComponents> getComponents() {
        return components;
    }

    @NotBlank
    @Column(name = "PRD_NAME")
    private String name;

    @Min(1)
    @Max(3)
    @Column(name = "TYPE")
    private int type; // 1 = PRODUCT, 2 = CHOICE, 3 = VALUE MEAL

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    @JsonManagedReference  // Adicionando aqui
    private ProductStatus status;

    @OneToMany(mappedBy = "parentProduct", cascade = CascadeType.ALL)
    private List<ProductComponents> components = new ArrayList<>();

    public void addComponent(Product component) {
        ProductComponents productComponent = new ProductComponents(this, component);
        components.add(productComponent);
    }


    public Product(long id, int type) {
        this.id = id;
        this.type = type;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public void setComponents(List<ProductComponents> components) {
        this.components = components;
    }
}
