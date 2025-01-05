package com.rdi.MenuApp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "PRODUCT_STATUS")
@NoArgsConstructor
@AllArgsConstructor
public class ProductStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "STATUS")
    private int status; // 1 OR 0

    @OneToOne
    @JoinColumn(name = "PRD_ID", referencedColumnName = "PRD_ID")
    @JsonBackReference
    private Product product;


    public void setStatus(int status) {
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }


    public Long getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    public Product getProduct() {
        return product;
    }
}
