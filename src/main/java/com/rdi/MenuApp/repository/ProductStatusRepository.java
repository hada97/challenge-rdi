package com.rdi.MenuApp.repository;

import com.rdi.MenuApp.domain.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductStatusRepository extends JpaRepository<ProductStatus, Long> {

    @Query("SELECT p.status FROM ProductStatus p WHERE p.product.id = :productId")
    int findStatusByProductId(@Param("productId") Long productId);


    @Modifying
    @Query("UPDATE ProductStatus p SET p.status = :status WHERE p.product.id = :productId")
    void updateStatus(@Param("productId") Long productId, @Param("status") int status);


}
