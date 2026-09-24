package com.kafe.koffee.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kafe.koffee.entities.MenuItem;
import com.kafe.koffee.enums.CoffeeType;
import com.kafe.koffee.exception.CategoryNotFoundException;
import com.kafe.koffee.exception.MenuItemNotFoundException;
import com.kafe.koffee.repository.CategoryRepository;
import com.kafe.koffee.repository.MenuItemRepository;
import com.kafe.koffee.entities.Category;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuItemService {
	
	private final MenuItemRepository menuItemRepository;
    private final CategoryRepository categoryRepository;
    
    //Available MenuItems
    
    @Transactional(readOnly = true)
    public List<MenuItem> getAvailableItems() {

        return menuItemRepository.findByAvailableTrue();
    }
    
    //Get MenuItem by Id
    
    @Transactional(readOnly = true)
    public MenuItem getById(Long id) {

        return menuItemRepository.findById(id)
                .orElseThrow(() ->
                        new MenuItemNotFoundException(
                                "Menu item not found with id: " + id
                        )
                );
    }
    
    //MenuItem by Category
    
    @Transactional(readOnly = true)
    public List<MenuItem> getByCategory(Long categoryId) {

        // First verify that the category exists
        categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new CategoryNotFoundException(
                                "Category not found with id: " + categoryId
                        )
                );

        return menuItemRepository.findByCategoryId(categoryId);
    }
    
    //MenuItem by CoffeeType
    
    @Transactional(readOnly = true)
    public List<MenuItem> getByCoffeeType(
            CoffeeType coffeeType) {

        return menuItemRepository
                .findByCoffeeTypeAndAvailableTrue(
                        coffeeType
                );
    }
    
    //Search Menu Items
    
    @Transactional(readOnly = true)
    public List<MenuItem> search(String name) {

        return menuItemRepository
                .findByNameContainingIgnoreCase(name);
    }
    
    //Create MenuItem
    
    public MenuItem create(MenuItem request) {

        if (request.getCategory() != null) {

            Long categoryId =
                    request.getCategory().getId();

            Category category =
                    categoryRepository.findById(categoryId)
                            .orElseThrow(() ->
                                    new CategoryNotFoundException(
                                            "Category not found with id: "
                                                    + categoryId
                                    )
                            );

            request.setCategory(category);
        }

        return menuItemRepository.save(request);
    }
    
    //Update MenuItem
    
    public MenuItem update(
            Long id,
            MenuItem request) {

        MenuItem item = getById(id);

        // Update basic information
        item.setName(request.getName());

        item.setDescription(
                request.getDescription()
        );

        item.setPrice(
                request.getPrice()
        );

        item.setCoffeeType(
                request.getCoffeeType()
        );

        item.setAvailable(
                request.isAvailable()
        );

        item.setImageUrl(
                request.getImageUrl()
        );


        // Update category
        if (request.getCategory() != null) {

            Long categoryId =
                    request.getCategory().getId();

            Category category =
                    categoryRepository.findById(categoryId)
                            .orElseThrow(() ->
                                    new CategoryNotFoundException(
                                            "Category not found with id: "
                                                    + categoryId
                                    )
                            );

            item.setCategory(category);
        }

        return menuItemRepository.save(item);
    }
    
    //Delete MenuItem
    
    public void delete(Long id) {

        MenuItem item = getById(id);

        menuItemRepository.delete(item);
    }
    
}
