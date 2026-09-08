package com.kafe.koffee.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

import com.kafe.koffee.enums.CoffeeType;
@Entity
@Table(name = "menu_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "category")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class MenuItem {

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @EqualsAndHashCode.Include
	    private Long id;

	    @Column(nullable = false)
	    private String name;
	    
	    @Column(columnDefinition = "TEXT")
	    private String description;

	    @Column(nullable = false, precision = 10, scale = 2)
	    private BigDecimal price;

	    @Enumerated(EnumType.STRING)
	    private CoffeeType coffeeType;

	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "category_id")
	    private Category category;

	    @Column(nullable = false)
	    @Builder.Default
	    private boolean available = true;

	    private String imageUrl;


}
