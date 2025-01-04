package com.rdi.MenuApp.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRODUCT_COMPONENTS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductComponents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRD_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "PRD_PARENT_ID")
    private Product parentProduct;

    @ManyToOne
    @JoinColumn(name = "CHILD_PRD_ID")
    private Product childProduct;

}
