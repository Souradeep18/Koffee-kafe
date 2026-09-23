package com.kafe.koffee.controllers;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kafe.koffee.entities.Coupon;
import com.kafe.koffee.services.CouponService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CouponController {
	
	private final CouponService couponService;
	
	//ADMIN
	
	@PostMapping("/admin/coupons") 
	@PreAuthorize("hasRole('ADMIN')") 
	public ResponseEntity<Coupon> createCoupon( @RequestBody Coupon coupon) { 
		Coupon createdCoupon = couponService.createCoupon(coupon); 
		return ResponseEntity 
				.status(HttpStatus.CREATED) 
				.body(createdCoupon); 
		}
	// Get all coupons 
	
	@GetMapping("/admin/coupons")
	@PreAuthorize("hasRole('ADMIN')") 
	public ResponseEntity<List<Coupon>> getAllCoupons() { 
		List<Coupon> coupons = couponService.getAllCoupons(); 
		if (coupons.isEmpty()) { 
			return ResponseEntity 
					.status(HttpStatus.NO_CONTENT) 
					.build(); 
			} 
		return ResponseEntity 
				.status(HttpStatus.OK) 
				.body(coupons); 
		}
	
	// Get coupon by ID
	
	@GetMapping("/admin/coupons/{id}")
	@PreAuthorize("hasRole('ADMIN')") 
	public ResponseEntity<Coupon> getCouponById( @PathVariable Long id) { 
		Coupon coupon = couponService.getCouponById(id); 
		return ResponseEntity 
				.status(HttpStatus.OK) 
				.body(coupon); 
		}
	
	// Update coupon
	
	@PatchMapping("/admin/coupons/{id}")
	@PreAuthorize("hasRole('ADMIN')") 
	public ResponseEntity<Coupon> updateCoupon( @PathVariable Long id, @RequestBody Coupon coupon) {
		Coupon updatedCoupon = couponService.updateCoupon(id, coupon); 
		return ResponseEntity 
				.status(HttpStatus.OK) 
				.body(updatedCoupon); 
		}
	
	// Delete coupon
	
	@DeleteMapping("/admin/coupons/{id}") 
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> deleteCoupon( @PathVariable Long id) { 
		couponService.deleteCoupon(id); 
		return ResponseEntity 
				.status(HttpStatus.NO_CONTENT) 
				.build(); 
		}
	
	//CUSTOMER
	
	// Get currently active and valid coupons
	
	@GetMapping("/coupons") 
	public ResponseEntity<List<Coupon>> getCurrentlyValidCoupons() { 
		List<Coupon> coupons = couponService.getCurrentlyValidCoupons(); 
		if (coupons.isEmpty()) { 
			return ResponseEntity 
					.status(HttpStatus.NO_CONTENT) 
					.build(); 
			} 
		return ResponseEntity 
				.status(HttpStatus.OK) 
				.body(coupons); 
		}
	
	// Get coupon by code
	
	@GetMapping("/coupons/{code}") 
	public ResponseEntity<Coupon> getCouponByCode( @PathVariable String code) { 
		Coupon coupon = couponService.getCouponByCode(code); 
		return ResponseEntity 
				.status(HttpStatus.OK) 
				.body(coupon); 
		}
	
	// Validate coupon against order amount
	
	@GetMapping("/coupons/{code}/validate") 
	public ResponseEntity<Coupon> validateCoupon( @PathVariable String code, @RequestParam BigDecimal orderAmount) { 
		if (orderAmount == null || orderAmount.compareTo(BigDecimal.ZERO) <= 0) { 
			return ResponseEntity 
					.status(HttpStatus.BAD_REQUEST) 
					.build(); 
			}
		Coupon coupon = couponService.validateCoupon(code, orderAmount); 
		return ResponseEntity 
				.status(HttpStatus.OK) 
				.body(coupon);
	}
	
	// Calculate discount
	
	@GetMapping("/coupons/{code}/discount")
	public ResponseEntity<BigDecimal> calculateDiscount( @PathVariable String code, @RequestParam BigDecimal orderAmount) { 
		if (orderAmount == null || orderAmount.compareTo(BigDecimal.ZERO) <= 0) { 
			return ResponseEntity 
					.status(HttpStatus.BAD_REQUEST) 
					.build(); 
			}
		BigDecimal discount = couponService.calculateDiscount( code, orderAmount ); 
		return ResponseEntity 
				.status(HttpStatus.OK) 
				.body(discount); 
		}
	
}
