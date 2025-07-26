package com.genzfashion.backend.repository;

import com.genzfashion.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Spring Data JPA sẽ tự động tạo các phương thức CRUD cơ bản
    // như findAll(), findById(), save(), delete(),...
}