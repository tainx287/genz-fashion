package com.ecommerce.genz_fashion.repository.product;


import com.ecommerce.genz_fashion.entity.ProductVariants;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface VariantRepository extends JpaRepository<ProductVariants, Long> {

    @Query("SELECT p FROM ProductVariants p")
    Page<ProductVariants> findALL(Pageable pageable);
}
