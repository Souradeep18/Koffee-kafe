package com.kafe.koffee.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
//import java.util.Optional;

import org.springframework.stereotype.Service;

import com.kafe.koffee.entities.Coupon;
//import com.kafe.koffee.exception.ResourceNotFoundException;
//import com.kafe.koffee.exception.ConflictException;
import com.kafe.koffee.exception.CouponNotFoundException;
import com.kafe.koffee.exception.DuplicateResourceException;
import com.kafe.koffee.repository.CouponRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponService {
	
	 private final CouponRepository couponRepository;

	    // Create coupon
	    public Coupon createCoupon(Coupon coupon) {

	        if (couponRepository.existsByCode(coupon.getCode())) {
	            throw new DuplicateResourceException(
	                    "Coupon already exists with code: " + coupon.getCode()
	            );
	        }

	        return couponRepository.save(coupon);
	    }
	    
	 // Get coupon by ID
	    @Transactional(readOnly = true)
	    public Coupon getCouponById(Long id) {

	        return couponRepository.findById(id)
	                .orElseThrow(() ->
	                        new CouponNotFoundException(
	                                "Coupon not found with id: " + id
	                        )
	                );
	    }
	 // Get coupon by code
	    @Transactional(readOnly = true)
	    public Coupon getCouponByCode(String code) {

	        return couponRepository.findByCode(code)
	                .orElseThrow(() ->
	                        new CouponNotFoundException(
	                                "Coupon not found with code: " + code
	                        )
	                );
	    }
	    
	    // Get all coupons
	    @Transactional(readOnly = true)
	    public List<Coupon> getAllCoupons() {

	        return couponRepository.findAll();
	    }
	    
	 // Get currently valid coupons
	    @Transactional(readOnly = true)
	    public List<Coupon> getCurrentlyValidCoupons() {

	        LocalDateTime now = LocalDateTime.now();

	        return couponRepository
	                .findByActiveTrueAndValidFromLessThanEqualAndValidUntilGreaterThanEqual(
	                        now,
	                        now
	                );
	    }
	    
	 // Update coupon
	    public Coupon updateCoupon(Long id, Coupon updatedCoupon) {

	        Coupon existingCoupon = getCouponById(id);

	        // If code is being changed, make sure the new code is not already used
	        if (!existingCoupon.getCode().equals(updatedCoupon.getCode())
	                && couponRepository.existsByCode(updatedCoupon.getCode())) {

	            throw new DuplicateResourceException(
	                    "Coupon already exists with code: "
	                            + updatedCoupon.getCode()
	            );
	        }
	        existingCoupon.setCode(updatedCoupon.getCode());
	        existingCoupon.setDiscountPercentage(
	                updatedCoupon.getDiscountPercentage()
	        );
	        existingCoupon.setMinimumOrderAmount(
	                updatedCoupon.getMinimumOrderAmount()
	        );
	        existingCoupon.setValidFrom(updatedCoupon.getValidFrom());
	        existingCoupon.setValidUntil(updatedCoupon.getValidUntil());
	        existingCoupon.setActive(updatedCoupon.isActive());

	        return couponRepository.save(existingCoupon);
	    }
	    
	 // Delete coupon
	    public void deleteCoupon(Long id) {

	        Coupon coupon = getCouponById(id);

	        couponRepository.delete(coupon);
	    }
	    
	 // Validate coupon for an order amount
	    @Transactional(readOnly = true)
	    public Coupon validateCoupon(String code, BigDecimal orderAmount) {

	        Coupon coupon = getCouponByCode(code);

	        LocalDateTime now = LocalDateTime.now();

	        if (!coupon.isActive()) {
	            throw new CouponNotFoundException(
	                    "Coupon is not active: " + code
	            );
	        }
	        
	        if (coupon.getValidFrom() != null
	                && coupon.getValidFrom().isAfter(now)) {

	            throw new CouponNotFoundException(
	                    "Coupon is not valid yet: " + code
	            );
	        }

	        if (coupon.getValidUntil() != null
	                && coupon.getValidUntil().isBefore(now)) {

	            throw new CouponNotFoundException(
	                    "Coupon has expired: " + code
	            );
	        }
	        
	        if (coupon.getMinimumOrderAmount() != null
	                && orderAmount.compareTo(coupon.getMinimumOrderAmount()) < 0) {

	            throw new IllegalArgumentException(
	                    "Minimum order amount for coupon "
	                            + code
	                            + " is "
	                            + coupon.getMinimumOrderAmount()
	            );
	        }

	        return coupon;
	    }
	    
	 // Calculate discount
	    @Transactional(readOnly = true)
	    public BigDecimal calculateDiscount(
	            String code,
	            BigDecimal orderAmount) {

	        Coupon coupon = validateCoupon(code, orderAmount);

	        return orderAmount
	                .multiply(coupon.getDiscountPercentage())
	                .divide(BigDecimal.valueOf(100));
	    }

	

}
