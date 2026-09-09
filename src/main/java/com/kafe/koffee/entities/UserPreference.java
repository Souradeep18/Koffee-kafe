package com.kafe.koffee.entities;

import com.kafe.koffee.enums.CoffeeType;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_preferences")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "user")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class UserPreference {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "user_id",
        nullable = false,
        unique = true
    )
    private User user;

    @Enumerated(EnumType.STRING)
    private CoffeeType favoriteCoffeeType;

    private String preferredSize;

    private Boolean prefersHot;
    
    private Boolean prefersMilk;

    private Boolean prefersSweet;

    private Boolean prefersStrongCoffee;

}
