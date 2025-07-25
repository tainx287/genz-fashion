package com.ecommerce.genz_fashion.service;

import com.ecommerce.genz_fashion.entity.Reviews;
import com.ecommerce.genz_fashion.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    
    private final ReviewRepository reviewRepository;
    
    public List<Reviews> getAllReviews() {
        return reviewRepository.findAll();
    }
    
    public Optional<Reviews> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }
    
    public List<Reviews> getReviewsByProduct(Long productId) {
        return reviewRepository.findByProductIdOrderByCreatedAtDesc(productId);
    }
    
    public List<Reviews> getReviewsByUser(Long userId) {
        return reviewRepository.findByUserId(userId);
    }
    
    public List<Reviews> getReviewsByRating(Integer rating) {
        return reviewRepository.findByRating(rating);
    }
    
    public Reviews createReview(Reviews review) {
        // Check if user already reviewed this product
        if (reviewRepository.existsByUserIdAndProductId(review.getUserId(), 
                review.getProductId())) {
            throw new RuntimeException("User has already reviewed this product");
        }
        
        review.setCreatedAt(new Date());
        return reviewRepository.save(review);
    }
    
    public Reviews updateReview(Long id, Reviews reviewDetails) {
        Reviews review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        
        review.setRating(reviewDetails.getRating());
        review.setComment(reviewDetails.getComment());
        review.setUpdatedAt(new Date());
        
        return reviewRepository.save(review);
    }
    
    public void deleteReview(Long id) {
        Reviews review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        reviewRepository.delete(review);
    }
    
    public Double getAverageRatingByProduct(Long productId) {
        Double average = reviewRepository.getAverageRatingByProduct(productId);
        return average != null ? average : 0.0;
    }
    
    public Long getReviewCountByProduct(Long productId) {
        return reviewRepository.getReviewCountByProduct(productId);
    }
    
    public List<Object[]> getRatingDistributionByProduct(Long productId) {
        return reviewRepository.getRatingDistributionByProduct(productId);
    }
    
    public boolean hasUserReviewedProduct(Long userId, Long productId) {
        return reviewRepository.existsByUserIdAndProductId(userId, productId);
    }
}