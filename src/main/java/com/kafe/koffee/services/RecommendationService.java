package com.kafe.koffee.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kafe.koffee.entities.MenuItem;
import com.kafe.koffee.entities.User;
import com.kafe.koffee.entities.UserPreference;
import com.kafe.koffee.enums.OrderStatus;
import com.kafe.koffee.repository.MenuItemRepository;
import com.kafe.koffee.repository.OrderRepository;
import com.kafe.koffee.repository.UserPreferenceRepository;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendationService {
	
    private final UserPreferenceRepository preferenceRepository;
    private final MenuItemRepository menuItemRepository;
    private final OrderRepository orderRepository;
    
    @Transactional(readOnly = true)
    public List<MenuItem> recommend(User user) {

        // Check whether the user has completed at least 5 purchases
        long successfulPurchases =
                orderRepository.countByUserIdAndStatus(
                        user.getId(),
                        OrderStatus.COMPLETED
                );

        // Recommendations are available only after 5 completed purchases
        if (successfulPurchases < 5) {
            return List.of();
        }

        // Find user preferences
        UserPreference preference =
                preferenceRepository.findByUserId(user.getId())
                        .orElse(null);

        // If user has selected a favorite coffee type,
        // return available items of that type
        if (preference != null
                && preference.getFavoriteCoffeeType() != null) {

            return menuItemRepository
                    .findByCoffeeTypeAndAvailableTrue(
                            preference.getFavoriteCoffeeType()
                    );
        }
        
        // No preference available
        // Return top 5 available menu items
        return menuItemRepository
                .findTop5ByAvailableTrueOrderByIdAsc();
    }

}
