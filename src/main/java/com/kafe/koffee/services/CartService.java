package com.kafe.koffee.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kafe.koffee.entities.Cart;
import com.kafe.koffee.entities.CartItem;
import com.kafe.koffee.entities.MenuItem;
import com.kafe.koffee.entities.User;
import com.kafe.koffee.exception.BadRequestException;
import com.kafe.koffee.exception.CartItemNotFoundException;
import com.kafe.koffee.exception.MenuItemNotFoundException;
import com.kafe.koffee.exception.UserNotFoundException;
import com.kafe.koffee.repository.CartItemRepository;
import com.kafe.koffee.repository.CartRepository;
import com.kafe.koffee.repository.MenuItemRepository;
import com.kafe.koffee.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {
	
	private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final MenuItemRepository menuItemRepository;
    private final UserRepository userRepository;
    
    //Get Email by User
    
    private User getUser(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UserNotFoundException(
                                "User not found with email: " + email
                        )
                );
    }
    
    //Get or create cart
    
    public Cart getOrCreateCart(String email) {

        User user = getUser(email);

        return cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart cart = Cart.builder()
                            .user(user)
                            .build();
                    return cartRepository.save(cart);
                });
    }
    
    //Get CartItems
    
    @Transactional(readOnly = true)
    public List<CartItem> getItems(String email) {

        Cart cart = getOrCreateCart(email);

        return cartItemRepository.findByCartId(cart.getId());
    }
    
    //Add item to Cart
    
    public CartItem addItem(String email,Long menuItemId,Integer quantity) {

        if (quantity == null || quantity <= 0) {
            throw new BadRequestException(
                    "Quantity must be greater than zero"
            );
        }

        Cart cart = getOrCreateCart(email);
        
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() ->
                        new MenuItemNotFoundException(
                                "Menu item not found with id: " + menuItemId
                        )
                );

        if (!menuItem.isAvailable()) {
            throw new BadRequestException(
                    "Menu item is currently unavailable"
            );
        }
        
     // Check whether this menu item already exists in the cart
        CartItem existing = cartItemRepository.findByCartIdAndMenuItemId(cart.getId(),menuItemId).orElse(null);
        
        if (existing != null) {

            existing.setQuantity(existing.getQuantity() + quantity);

            return cartItemRepository.save(existing);
        }

        CartItem item = CartItem.builder()
                .cart(cart)
                .menuItem(menuItem)
                .quantity(quantity)
                .build();

        return cartItemRepository.save(item);
    }
    
    //Update Cart item
    
    public CartItem updateItem(String email,Long itemId,Integer quantity) {

        if (quantity == null || quantity <= 0) {
            throw new BadRequestException(
                    "Quantity must be greater than zero"
            );
        }
        
        Cart cart = getOrCreateCart(email);

        CartItem item = cartItemRepository.findByIdAndCartId(itemId,cart.getId())
                .orElseThrow(() ->
                        new CartItemNotFoundException(
                                "Cart item not found with id: " + itemId
                        )
                );

        item.setQuantity(quantity);

        return cartItemRepository.save(item);
    }
    
    //Remove item from Cart
    
    public void removeItem(String email,Long itemId) {

        Cart cart = getOrCreateCart(email);

        CartItem item = cartItemRepository.findByIdAndCartId(itemId,cart.getId())
                .orElseThrow(() ->
                        new CartItemNotFoundException(
                                "Cart item not found with id: " + itemId
                        )
                );

        cartItemRepository.delete(item);
    }
    
    //clear cart
    
    public void clearCart(String email) {

        Cart cart = getOrCreateCart(email);

        cartItemRepository.deleteByCartId(cart.getId());
        
    }
        


}
