package com.kafe.koffee.dtos.profile;

import com.kafe.koffee.enums.CoffeeType;

public record PreferenceResponse(
        CoffeeType favoriteCoffeeType,

        String preferredSize,

        Boolean prefersHot,

        Boolean prefersMilk,

        Boolean prefersSweet,

        Boolean prefersStrongCoffee) {

}
