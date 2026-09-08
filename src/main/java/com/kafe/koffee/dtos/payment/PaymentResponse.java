package com.kafe.koffee.dtos.payment;

import java.math.BigDecimal;

import com.kafe.koffee.enums.PaymentStatus;

public record PaymentResponse(
        Long paymentId,
        Long orderId,
        BigDecimal amount,
        PaymentStatus status,
        String transactionId
        ) {

}
