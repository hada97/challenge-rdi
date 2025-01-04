package com.rdi.MenuApp.repository;

import com.rdi.MenuApp.domain.ProductComponents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProductComponentsRepository extends JpaRepository<ProductComponents, Long> {

    // Corrigido o parâmetro e a consulta para usar o campo 'parentProduct'
    @Query("SELECT c.childProduct.id FROM ProductComponents c WHERE c.parentProduct.id = :parentProductId")
    List<Long> findByParentId(@Param("parentProductId") Long parentProductId);
}
