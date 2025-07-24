package com.ecommerce.genz_fashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ecommerce.genz_fashion.entity.Colors;

@Repository
public interface ColorsRepository extends JpaRepository<Colors, Long> {
    
}