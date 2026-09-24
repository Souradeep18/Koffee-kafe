package com.kafe.koffee.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import com.kafe.koffee.entities.Review;
import com.kafe.koffee.services.ReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {
	
	private final ReviewService reviewService;
	
	//get reviews for a menuItem
	
	 @GetMapping("/menu-item/{menuItemId}")
	    public ResponseEntity<List<Review>> getForMenuItem(@PathVariable Long menuItemId) {

	        List<Review> reviews =
	                reviewService.getForMenuItem(menuItemId);

	        if (reviews.isEmpty()) {
	            return ResponseEntity
	                    .status(HttpStatus.NO_CONTENT)
	                    .build();
	        }

	        return ResponseEntity
	                .status(HttpStatus.OK)
	                .body(reviews);
	    }
	 
	 //Get my reviews
	 
	 @GetMapping("/my")
	    public ResponseEntity<List<Review>> getMyReviews(Authentication authentication) {

	        String email = authentication.getName();

	        List<Review> reviews =
	                reviewService.getMyReviews(email);

	        if (reviews.isEmpty()) {
	            return ResponseEntity
	                    .status(HttpStatus.NO_CONTENT)
	                    .build();
	        }

	        return ResponseEntity
	                .status(HttpStatus.OK)
	                .body(reviews);
	    }
	 
	 //Create review
	 
	 @PostMapping("/menu-item/{menuItemId}")
	    public ResponseEntity<Review> create(
	            @PathVariable Long menuItemId,
	            @RequestParam Integer rating,
	            @RequestParam(required = false) String comment,
	            Authentication authentication) {

	        String email = authentication.getName();

	        Review review = reviewService.create(email,menuItemId,rating,comment);

	        return ResponseEntity
	                .status(HttpStatus.CREATED)
	                .body(review);
	    }
	 
	 //Delete my review
	 
	 @DeleteMapping("/{reviewId}")
	    public ResponseEntity<Void> delete(@PathVariable Long reviewId,Authentication authentication) {

	        String email = authentication.getName();

	        reviewService.delete(email, reviewId);

	        return ResponseEntity
	                .status(HttpStatus.NO_CONTENT)
	                .build();
	    }


}
