package com.kafe.koffee.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kafe.koffee.entities.Review;
import com.kafe.koffee.entities.User;
import com.kafe.koffee.entities.MenuItem;
import com.kafe.koffee.exception.BadRequestException;
import com.kafe.koffee.exception.DuplicateResourceException;
import com.kafe.koffee.exception.MenuItemNotFoundException;
import com.kafe.koffee.exception.ReviewNotFoundException;
import com.kafe.koffee.exception.UnauthorizedException;
import com.kafe.koffee.exception.UserNotFoundException;
import com.kafe.koffee.repository.MenuItemRepository;
import com.kafe.koffee.repository.ReviewRepository;
import com.kafe.koffee.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
	
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    
    //get user by Email
    
    private User getUser(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: " + email
                        )
                );
    }
    
    //Get reviews for a menuItem
    
    @Transactional(readOnly = true)
    public List<Review> getForMenuItem(Long menuItemId) {

        menuItemRepository.findById(menuItemId)
                .orElseThrow(() ->
                        new MenuItemNotFoundException(
                                "Menu item not found with id: " + menuItemId
                        )
                );

        return reviewRepository
                .findByMenuItemIdOrderByCreatedAtDesc(menuItemId);
    }
    
    //get logged-in user's reviews
    
    @Transactional(readOnly = true)
    public List<Review> getMyReviews(String email) {

        User user = getUser(email);

        return reviewRepository
                .findByUserIdOrderByCreatedAtDesc(user.getId());
    }
    
    //Create Review
    
    public Review create(String email,Long menuItemId,Integer rating,String comment) {

        User user = getUser(email);

        // Validate rating
        if (rating == null || rating < 1 || rating > 5) {
            throw new BadRequestException(
                    "Rating must be between 1 and 5"
            );
        }
        
        // Check menu item
        MenuItem item = menuItemRepository.findById(menuItemId)
                .orElseThrow(() ->
                        new MenuItemNotFoundException(
                                "Menu item not found with id: " + menuItemId
                        )
                );

        // Prevent duplicate review
        if (reviewRepository.existsByUserIdAndMenuItemId(
                user.getId(),
                menuItemId)) {

            throw new DuplicateResourceException(
                    "You have already reviewed this menu item"
            );
        }
        
        Review review = Review.builder()
                .user(user)
                .menuItem(item)
                .rating(rating)
                .comment(comment)
                .build();

        return reviewRepository.save(review);
    }
    
    //delete review
    
    public void delete(String email,Long reviewId) {

        User user = getUser(email);

        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() ->
                        new ReviewNotFoundException(
                                "Review not found with id: " + reviewId
                        )
                );

        // Make sure the review belongs to the logged-in user
        if (!review.getUser().getId().equals(user.getId())) {

            throw new UnauthorizedException(
                    "You cannot delete this review"
            );
        }

        reviewRepository.delete(review);
    }

}
