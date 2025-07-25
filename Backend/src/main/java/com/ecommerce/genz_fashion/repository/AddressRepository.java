package com.ecommerce.genz_fashion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ecommerce.genz_fashion.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    
}