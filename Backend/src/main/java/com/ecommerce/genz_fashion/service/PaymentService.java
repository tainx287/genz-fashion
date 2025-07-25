package com.ecommerce.genz_fashion.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.genz_fashion.entity.Payments;
import com.ecommerce.genz_fashion.repository.PaymentRepository;

@Service
@Transactional
public class PaymentService {
    
    @Autowired
    private PaymentRepository paymentRepository;
    
    public List<Payments> getAllPayments() {
        return paymentRepository.findAll();
    }
    
    public Optional<Payments> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }
    
    public List<Payments> getPaymentsByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }
    
    public Payments savePayment(Payments payment) {
        if (payment.getCreatedAt() == null) {
            payment.setCreatedAt(new Date());
        }
        return paymentRepository.save(payment);
    }
    
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }
    
    public Payments processPayment(Long orderId, Double amount, String paymentMethod) {
        Payments payment = new Payments();
        payment.setOrderId(orderId);
        payment.setAmount(amount);
        payment.setPaymentMethod(paymentMethod);
        payment.setPaymentDate(new Date());
        payment.setPaymentStatus("completed");
        payment.setCreatedAt(new Date());
        return savePayment(payment);
    }
    
    public boolean refundPayment(Long paymentId) {
        Optional<Payments> payment = getPaymentById(paymentId);
        if (payment.isPresent()) {
            payment.get().setPaymentStatus("refunded");
            savePayment(payment.get());
            return true;
        }
        return false;
    }
    
    public Double getTotalPaymentsByOrder(Long orderId) {
        Double total = paymentRepository.getTotalCompletedPaymentsByOrder(orderId);
        return total != null ? total : 0.0;
    }
}