package com.kafe.koffee.dtos.profile;

import com.kafe.koffee.enums.PaymentMethodType;

public record PaymentMethodResponse(
        Long id,

        PaymentMethodType type,

        String displayName,

        String provider,

        String maskedDetails,

        boolean defaultMethod
        ) {

}
