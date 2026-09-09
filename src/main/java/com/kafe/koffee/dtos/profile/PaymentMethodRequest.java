package com.kafe.koffee.dtos.profile;

import com.kafe.koffee.enums.PaymentMethodType;

import jakarta.validation.constraints.NotNull;

public record PaymentMethodRequest(
        @NotNull(message = "Payment method type is required")
        PaymentMethodType type,
        
        String displayName,

        String provider,

        String maskedDetails,

        String gatewayToken,

        boolean defaultMethod
) {

}
