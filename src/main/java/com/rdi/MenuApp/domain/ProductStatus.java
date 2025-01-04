package com.rdi.MenuApp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRODUCT_STATUS")
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"product"})  // Ignorando a referência para Product
public class ProductStatus {

    public void setStatus(int status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "STATUS")
    private int status; // 1 OR 0

    @OneToOne
    @JoinColumn(name = "PRD_ID", referencedColumnName = "PRD_ID")
    @JsonBackReference  // Adicionando aqui
    private Product product;

}
