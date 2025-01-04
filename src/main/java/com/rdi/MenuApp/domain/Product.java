package com.rdi.MenuApp.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "PRODUCT")
@NoArgsConstructor
@AllArgsConstructor
public class Product {


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

    @Column(name = "PRD_NAME")
    private String name;

    @Column(name = "TYPE")
    private int type; // 1 = PRODUCT, 2 = CHOICE, 3 = VALUE MEAL

    @OneToOne(mappedBy = "product", cascade = CascadeType.ALL)
    private ProductStatus status;

    @OneToMany(mappedBy = "parentProduct", cascade = CascadeType.ALL)
    private List<ProductComponents> components = new ArrayList<>();


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
