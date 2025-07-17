package com.ecommerce.genz_fashion.repository;

import com.ecommerce.genz_fashion.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByUsername(String username);
    
    Optional<User> findByEmail(String email);
    
    boolean existsByUsername(String username);
    
    boolean existsByEmail(String email);
    
    List<User> findByIsActiveTrue();
    
    List<User> findByRole(User.Role role);
    
    @Query("SELECT u FROM User u WHERE u.fullName LIKE %:name% AND u.isActive = true")
    List<User> findByFullNameContainingAndIsActiveTrue(@Param("name") String name);
}