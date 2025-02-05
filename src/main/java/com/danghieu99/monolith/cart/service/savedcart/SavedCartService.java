package com.danghieu99.monolith.cart.service.savedcart;

import com.danghieu99.monolith.cart.entity.SavedCart;
import com.danghieu99.monolith.cart.repository.jpa.SavedCartRepository;
import com.danghieu99.monolith.common.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

@Service
public class SavedCartService {

    private final SavedCartRepository savedCartRepository;

    public SavedCartService(SavedCartRepository savedCartRepository) {
        this.savedCartRepository = savedCartRepository;
    }

    public SavedCart getByUserId(int userId) {
        return savedCartRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("Cart", "id", userId));
    }

    @Transactional
    public void save(@NotNull SavedCart savedCart) {
        if (savedCart.getId() != null) {
            throw new IllegalArgumentException("New Cart id must not be null");
        }
        savedCartRepository.save(savedCart);
    }

    @Transactional
    public void deleteByUserId(int userId) {
        savedCartRepository.deleteById(userId);
    }
}