package com.ecommerce.genz_fashion.repository.orders;


import com.ecommerce.genz_fashion.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {



}
