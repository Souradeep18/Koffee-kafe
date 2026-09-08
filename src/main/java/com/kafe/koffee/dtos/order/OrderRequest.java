package com.kafe.koffee.dtos.order;

import java.util.List;

public record OrderRequest(
        Long addressId,
        Long couponId,
        List<OrderItemRequest> items
        ) {

}
