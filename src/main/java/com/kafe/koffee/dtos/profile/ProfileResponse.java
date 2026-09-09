package com.kafe.koffee.dtos.profile;

import java.time.LocalDate;
import java.util.List;

import com.kafe.koffee.enums.Gender;

public record ProfileResponse(
		Long userId,

        String name,

        String email,

        LocalDate dateOfBirth,

        Gender gender,

        PreferenceResponse preferences,

        List<ProfileCouponResponse> validCoupons,

        List<PaymentMethodResponse> paymentMethods,

        List<ProfileOrderResponse> orderHistory,

        List<ProfileAddressResponse> addressHistory,
        
        List<ProfileReviewResponse> reviewHistory,

        List<BeverageRecommendationResponse> recommendations
        ) {

}
