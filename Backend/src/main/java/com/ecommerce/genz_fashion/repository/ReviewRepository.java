package com.ecommerce.genz_fashion.repository;

import com.ecommerce.genz_fashion.entity.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Reviews, Long> {
    
    List<Reviews> findByProductId(Long productId);
    
    List<Reviews> findByUserId(Long userId);
    
    List<Reviews> findByProductIdOrderByCreatedAtDesc(Long productId);
    
    List<Reviews> findByRating(Integer rating);
    
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.product.productId = :productId")
    Double getAverageRatingByProduct(@Param("productId") Long productId);
    
    @Query("SELECT COUNT(r) FROM Review r WHERE r.product.productId = :productId")
    Long getReviewCountByProduct(@Param("productId") Long productId);
    
    @Query("SELECT r.rating, COUNT(r) FROM Review r WHERE r.product.productId = :productId GROUP BY r.rating ORDER BY r.rating DESC")
    List<Object[]> getRatingDistributionByProduct(@Param("productId") Long productId);
    
    boolean existsByUserIdAndProductId(Long userId, Long productId);
}