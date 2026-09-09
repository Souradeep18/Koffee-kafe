package com.kafe.koffee.dtos.profile;

import com.kafe.koffee.enums.CoffeeType;

public record PreferenceRequest(
		
        CoffeeType favoriteCoffeeType,

        String preferredSize,

        Boolean prefersHot,

        Boolean prefersMilk,

        Boolean prefersSweet,

        Boolean prefersStrongCoffee) {

}
