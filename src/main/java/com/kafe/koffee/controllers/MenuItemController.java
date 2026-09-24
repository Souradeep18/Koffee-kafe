package com.kafe.koffee.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kafe.koffee.entities.MenuItem;
import com.kafe.koffee.enums.CoffeeType;
import com.kafe.koffee.services.MenuItemService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/menu-items")
@RequiredArgsConstructor
public class MenuItemController {
	
	private final MenuItemService menuItemService;
	
    // GET ALL AVAILABLE MENU ITEMS

    @GetMapping
    public ResponseEntity<List<MenuItem>> getAvailableItems() {

        List<MenuItem> items = menuItemService.getAvailableItems();

        if (items.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(items);
    }
    
 // GET MENU ITEM BY ID

    @GetMapping("/{id}")
    public ResponseEntity<MenuItem> getById(@PathVariable Long id) {

        MenuItem item = menuItemService.getById(id);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(item);
    }
    
 // GET MENU ITEMS BY CATEGORY


    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<MenuItem>> getByCategory(@PathVariable Long categoryId) {

        List<MenuItem> items =
                menuItemService.getByCategory(categoryId);

        if (items.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(items);
    }
    
 // GET MENU ITEMS BY COFFEE TYPE

    @GetMapping("/coffee-type/{coffeeType}")
    public ResponseEntity<List<MenuItem>> getByCoffeeType(@PathVariable CoffeeType coffeeType) {

        List<MenuItem> items =
                menuItemService.getByCoffeeType(coffeeType);

        if (items.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(items);
    }
    
 // SEARCH MENU ITEMS BY NAME

    @GetMapping("/search")
    public ResponseEntity<List<MenuItem>> search(@RequestParam String name) {

        if (name == null || name.isBlank()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }

        List<MenuItem> items =
                menuItemService.search(name);

        if (items.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(items);
    }
    
    //for admin
    //Create menuItem
    
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MenuItem> create(@RequestBody MenuItem menuItem) {

        MenuItem createdItem =
                menuItemService.create(menuItem);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdItem);
    }
    
 // UPDATE MENU ITEM for Admin only

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MenuItem> update(@PathVariable Long id,@RequestBody MenuItem menuItem) {

        MenuItem updatedItem =
                menuItemService.update(id, menuItem);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(updatedItem);
    }
    
 // DELETE MENU ITEM for Admin only

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {

        menuItemService.delete(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }
    
}
