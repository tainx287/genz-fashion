package com.ecommerce.genz_fashion.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.genz_fashion.entity.PublicVouchers;
import com.ecommerce.genz_fashion.entity.MemberVouchers;
import com.ecommerce.genz_fashion.entity.UserVoucherUsage;
import com.ecommerce.genz_fashion.repository.PublicVoucherRepository;
import com.ecommerce.genz_fashion.repository.MemberVouchersRepository;
import com.ecommerce.genz_fashion.repository.UserVoucherUsageRepository;

@Service
@Transactional
public class VoucherService {
    
    @Autowired
    private PublicVoucherRepository publicVoucherRepository;
    
    @Autowired
    private MemberVouchersRepository memberVouchersRepository;
    
    @Autowired
    private UserVoucherUsageRepository userVoucherUsageRepository;
    
    // Public Voucher methods
    public List<PublicVouchers> getAllPublicVouchers() {
        return publicVoucherRepository.findAll();
    }
    
    public Optional<PublicVouchers> getPublicVoucherById(Long id) {
        return publicVoucherRepository.findById(id);
    }
    
    public Optional<PublicVouchers> getPublicVoucherByCode(String code) {
        return publicVoucherRepository.findAll().stream()
                .filter(voucher -> code.equals(voucher.getCode()))
                .findFirst();
    }
    
    public List<PublicVouchers> getActivePublicVouchers() {
        Date now = new Date();
        return publicVoucherRepository.findAll().stream()
                .filter(voucher -> voucher.getIsActive() != null && voucher.getIsActive())
                .filter(voucher -> voucher.getStartDate().before(now) && voucher.getEndDate().after(now))
                .toList();
    }
    
    // Member Voucher methods
    public List<MemberVouchers> getAllMemberVouchers() {
        return memberVouchersRepository.findAll();
    }
    
    public Optional<MemberVouchers> getMemberVoucherById(Long id) {
        return memberVouchersRepository.findById(id);
    }
    
    public Optional<MemberVouchers> getMemberVoucherByCode(String code) {
        return memberVouchersRepository.findAll().stream()
                .filter(voucher -> code.equals(voucher.getCode()))
                .findFirst();
    }
    
    public List<MemberVouchers> getActiveMemberVouchers() {
        Date now = new Date();
        return memberVouchersRepository.findAll().stream()
                .filter(voucher -> voucher.getIsActive() != null && voucher.getIsActive())
                .filter(voucher -> voucher.getStartDate().before(now) && voucher.getEndDate().after(now))
                .toList();
    }
    
    // Voucher usage methods
    public boolean canUseVoucher(String voucherCode, Long userId, Double orderAmount) {
        // Check public voucher
        Optional<PublicVouchers> publicVoucher = getPublicVoucherByCode(voucherCode);
        if (publicVoucher.isPresent()) {
            return validatePublicVoucher(publicVoucher.get(), userId, orderAmount);
        }
        
        // Check member voucher
        Optional<MemberVouchers> memberVoucher = getMemberVoucherByCode(voucherCode);
        if (memberVoucher.isPresent()) {
            return validateMemberVoucher(memberVoucher.get(), userId, orderAmount);
        }
        
        return false;
    }
    
    private boolean validatePublicVoucher(PublicVouchers voucher, Long userId, Double orderAmount) {
        Date now = new Date();
        return voucher.getIsActive() &&
               voucher.getStartDate().before(now) &&
               voucher.getEndDate().after(now) &&
               orderAmount >= voucher.getMinPurchaseAmount() &&
               (voucher.getUsageLimit() == null || voucher.getUsedCount() < voucher.getUsageLimit());
    }
    
    private boolean validateMemberVoucher(MemberVouchers voucher, Long userId, Double orderAmount) {
        Date now = new Date();
        long userUsageCount = userVoucherUsageRepository.findAll().stream()
                .filter(usage -> usage.getUserId().equals(userId))
                .filter(usage -> usage.getVoucherId().equals(voucher.getMemberVoucherId()))
                .filter(usage -> "member".equals(usage.getVoucherType()))
                .count();
        
        return voucher.getIsActive() &&
               voucher.getStartDate().before(now) &&
               voucher.getEndDate().after(now) &&
               orderAmount >= voucher.getMinPurchaseAmount() &&
               userUsageCount < voucher.getUsageLimitPerUser();
    }
    
    public Double calculateDiscount(String voucherCode, Double orderAmount) {
        Optional<PublicVouchers> publicVoucher = getPublicVoucherByCode(voucherCode);
        if (publicVoucher.isPresent()) {
            return calculatePublicVoucherDiscount(publicVoucher.get(), orderAmount);
        }
        
        Optional<MemberVouchers> memberVoucher = getMemberVoucherByCode(voucherCode);
        if (memberVoucher.isPresent()) {
            return calculateMemberVoucherDiscount(memberVoucher.get(), orderAmount);
        }
        
        return 0.0;
    }
    
    private Double calculatePublicVoucherDiscount(PublicVouchers voucher, Double orderAmount) {
        if ("percentage".equals(voucher.getDiscountType())) {
            Double discount = orderAmount * voucher.getDiscountValue() / 100;
            return voucher.getMaxDiscountAmount() != null ? 
                   Math.min(discount, voucher.getMaxDiscountAmount()) : discount;
        } else {
            return voucher.getDiscountValue();
        }
    }
    
    private Double calculateMemberVoucherDiscount(MemberVouchers voucher, Double orderAmount) {
        if ("percentage".equals(voucher.getDiscountType())) {
            Double discount = orderAmount * voucher.getDiscountValue() / 100;
            return voucher.getMaxDiscountAmount() != null ? 
                   Math.min(discount, voucher.getMaxDiscountAmount()) : discount;
        } else {
            return voucher.getDiscountValue();
        }
    }
    
    public void useVoucher(String voucherCode, Long userId, Long orderId) {
        UserVoucherUsage usage = new UserVoucherUsage();
        usage.setUserId(userId);
        usage.setOrderId(orderId);
        usage.setUsedAt(new Date());
        
        Optional<PublicVouchers> publicVoucher = getPublicVoucherByCode(voucherCode);
        if (publicVoucher.isPresent()) {
            usage.setVoucherType("public");
            usage.setVoucherId(publicVoucher.get().getVoucherId());
            publicVoucher.get().setUsedCount(publicVoucher.get().getUsedCount() + 1);
            publicVoucherRepository.save(publicVoucher.get());
        } else {
            Optional<MemberVouchers> memberVoucher = getMemberVoucherByCode(voucherCode);
            if (memberVoucher.isPresent()) {
                usage.setVoucherType("member");
                usage.setVoucherId(memberVoucher.get().getMemberVoucherId());
            }
        }
        
        userVoucherUsageRepository.save(usage);
    }
}