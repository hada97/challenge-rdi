package com.rdi.MenuApp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRODUCT_STATUS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ProductStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "STATUS")
    private int status; // 1 OR 0

    @OneToOne
    @JoinColumn(name = "PRD_ID", referencedColumnName = "PRD_ID")
    private Product product;

}
