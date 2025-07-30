package com.ecommerce.genz_fashion.repository.orders;


import com.ecommerce.genz_fashion.entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItems, Long> {

    @Query("SELECT o FROM OrderItems o WHERE o.order.orderId = :orderId")
    List<OrderItems> findByOrderId(Long orderId);


}
