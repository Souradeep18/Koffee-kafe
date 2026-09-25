package com.kafe.koffee.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kafe.koffee.entities.Cart;
import com.kafe.koffee.entities.CartItem;
import com.kafe.koffee.services.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {
	
	private final CartService cartService;
	
	//Get my cart
	
	@GetMapping
    public ResponseEntity<List<CartItem>> getCart(Authentication authentication) {

        String email = authentication.getName();

        List<CartItem> items =
                cartService.getItems(email);

        if (items.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(items);
    }
	
	//Get/Create my cart
	
	@GetMapping("/details")
    public ResponseEntity<Cart> getCartDetails(Authentication authentication) {

        String email = authentication.getName();

        Cart cart =cartService.getOrCreateCart(email);

        return ResponseEntity.status(HttpStatus.OK).body(cart);
        
    }
	
	//Add item to Cart
	
	@PostMapping("/items")
    public ResponseEntity<CartItem> addItem(@RequestParam Long menuItemId,@RequestParam Integer quantity,
    		Authentication authentication) {

        String email = authentication.getName();

        CartItem item = cartService.addItem(
                email,
                menuItemId,
                quantity
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(item);
    }
	
	//Update Cart
	
	@PatchMapping("/items/{itemId}")
    public ResponseEntity<CartItem> updateItem(@PathVariable Long itemId,@RequestParam Integer quantity,
            Authentication authentication) {

        String email = authentication.getName();

        CartItem item = cartService.updateItem(email,itemId,quantity);

        return ResponseEntity.status(HttpStatus.OK).body(item);
    }
	
	//Remove Cart Item
	
	@DeleteMapping("/items/{itemId}")
    public ResponseEntity<Void> removeItem(@PathVariable Long itemId,Authentication authentication) {

        String email = authentication.getName();

        cartService.removeItem(email,itemId);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
	
	//Clear Cart
	
	@DeleteMapping("/clear")
    public ResponseEntity<Void> clearCart(Authentication authentication) {

        String email = authentication.getName();

        cartService.clearCart(email);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        
    }

}
