package com.kafe.koffee.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;

import com.kafe.koffee.entities.Address;
import com.kafe.koffee.entities.Coupon;
import com.kafe.koffee.entities.MenuItem;
import com.kafe.koffee.entities.Order;
import com.kafe.koffee.entities.PaymentMethod;
import com.kafe.koffee.entities.Review;
import com.kafe.koffee.entities.User;
import com.kafe.koffee.entities.UserPreference;
import com.kafe.koffee.enums.Gender;
import com.kafe.koffee.exception.AddressNotFoundException;
import com.kafe.koffee.exception.DuplicateResourceException;
import com.kafe.koffee.exception.PaymentNotFoundException;
import com.kafe.koffee.exception.UserNotFoundException;
import com.kafe.koffee.repository.AddressRepository;
import com.kafe.koffee.repository.CouponRepository;
import com.kafe.koffee.repository.OrderRepository;
import com.kafe.koffee.repository.PaymentMethodRepository;
import com.kafe.koffee.repository.ReviewRepository;
import com.kafe.koffee.repository.UserPreferenceRepository;
import com.kafe.koffee.repository.UserRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileService {
	
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final UserPreferenceRepository preferenceRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final CouponRepository couponRepository;
    private final RecommendationService recommendationService;
    
    //for user
    
    private User getUser(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: " + email
                        )
                );
        }
    // Get profile
    
    @Transactional(readOnly = true)
    public User getProfile(String email) {

        return getUser(email);
    }
    
    // Update profile
    public User updateProfile(
            String currentEmail,
            String name,
            String newEmail,
            LocalDate dateOfBirth,
            Gender gender) {

        User user = getUser(currentEmail);

        // Update name
        if (name != null && !name.isBlank()) {
            user.setName(name);
        }

        // Update email
        if (newEmail != null
                && !newEmail.isBlank()
                && !newEmail.equalsIgnoreCase(user.getEmail())) {

            if (userRepository.existsByEmail(newEmail)) {
                throw new DuplicateResourceException(
                        "Email already in use: " + newEmail
                );
            }

            user.setEmail(newEmail);
        }

        // Update date of birth
        if (dateOfBirth != null) {
            user.setDateOfBirth(dateOfBirth);
        }

        // Update gender
        if (gender != null) {
            user.setGender(gender);
        }

        return userRepository.save(user);
    }
    
    //Address
    
    @Transactional(readOnly = true)
    public List<Address> getAddresses(String email) {

        User user = getUser(email);

        return addressRepository.findByUserId(user.getId());
    }


    // Add address
    public Address addAddress(
            String email,
            Address address) {

        User user = getUser(email);

        address.setUser(user);

        return addressRepository.save(address);
    }

 // Delete address
    public void deleteAddress(
            String email,
            Long addressId) {

        User user = getUser(email);

        Address address =
                addressRepository.findByIdAndUserId(
                        addressId,
                        user.getId()
                )
                .orElseThrow(() ->
                        new AddressNotFoundException(
                                "Address not found with id: "
                                        + addressId
                        )
                );

        addressRepository.delete(address);
    }
    
    //Orders
    
    @Transactional(readOnly = true)
    public List<Order> getOrderHistory(String email) {

        User user = getUser(email);

        return orderRepository
                .findByUserIdOrderByCreatedAtDesc(
                        user.getId()
                );
    }
    
    //Reviews
    
    @Transactional(readOnly = true)
    public List<Review> getReviewHistory(String email) {

        User user = getUser(email);

        return reviewRepository
                .findByUserIdOrderByCreatedAtDesc(
                        user.getId()
                );
    }
    
    //Coupons
    
    @Transactional(readOnly = true)
    public List<Coupon> getCoupons() {

        LocalDateTime now = LocalDateTime.now();

        return couponRepository
                .findByActiveTrueAndValidFromLessThanEqualAndValidUntilGreaterThanEqual(
                        now,
                        now
                );
    }
    
    //Payment Methods
    
    @Transactional(readOnly = true)
    public List<PaymentMethod> getPaymentMethods(
            String email) {

        User user = getUser(email);

        return paymentMethodRepository
                .findByUserIdAndActiveTrue(
                        user.getId()
                );
    }


    // Add payment method
    public PaymentMethod addPaymentMethod(
            String email,
            PaymentMethod method) {

        User user = getUser(email);

        method.setUser(user);

        return paymentMethodRepository.save(method);
    }

 // Remove payment method
    public void removePaymentMethod(
            String email,
            Long methodId) {

        User user = getUser(email);

        PaymentMethod method =
                paymentMethodRepository
                        .findByIdAndUserId(
                                methodId,
                                user.getId()
                        )
                        .orElseThrow(() ->
                                new PaymentNotFoundException(
                                        "Payment method not found with id: "
                                                + methodId
                                )
                        );

        // Soft delete
        method.setActive(false);

        paymentMethodRepository.save(method);
    }

    
    //Recommendations
    
    @Transactional(readOnly = true)
    public List<MenuItem> getRecommendations(
            String email) {

        User user = getUser(email);

        return recommendationService.recommend(user);
    }
    
    //User Preferences
    
 // Update preferences
    public UserPreference updatePreferences(
            String email,
            UserPreference request) {

        User user = getUser(email);

        UserPreference preference =
                preferenceRepository
                        .findByUserId(user.getId())
                        .orElseGet(() ->
                                UserPreference.builder()
                                        .user(user)
                                        .build()
                        );

        preference.setFavoriteCoffeeType(
                request.getFavoriteCoffeeType()
        );

        preference.setPreferredSize(
                request.getPreferredSize()
        );

        preference.setPrefersHot(
                request.getPrefersHot()
        );

        preference.setPrefersMilk(
                request.getPrefersMilk()
        );

        preference.setPrefersSweet(
                request.getPrefersSweet()
        );

        preference.setPrefersStrongCoffee(
                request.getPrefersStrongCoffee()
        );

        return preferenceRepository.save(preference);
    }


    // Get preferences
    @Transactional(readOnly = true)
    public UserPreference getPreferences(
            String email) {

        User user = getUser(email);

        return preferenceRepository
                .findByUserId(user.getId())
                .orElse(null);
    }
}
