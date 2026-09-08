package com.kafe.koffee.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "cart_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"cart", "menuItem"})
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class CartItem {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    /*
     * Many CartItems can belong to one Cart.
     *
     * cart_items.cart_id -> carts.id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "cart_id",
        nullable = false
    )
    private Cart cart;
    /*
     * Many CartItems can refer to the same MenuItem.
     *
     * cart_items.menu_item_id -> menu_items.id
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
        name = "menu_item_id",
        nullable = false
    )
    private MenuItem menuItem;

    @Column(nullable = false)
    private Integer quantity;

}
