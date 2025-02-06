package com.danghieu99.monolith.cart.service.cart;

import com.danghieu99.monolith.cart.entity.SaveCartItem;
import com.danghieu99.monolith.cart.repository.jpa.SaveCartItemRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaveCartService {

    private final SaveCartItemRepository saveCartItemRepository;

    public SaveCartService(SaveCartItemRepository saveCartItemRepository) {
        this.saveCartItemRepository = saveCartItemRepository;
    }

    public List<SaveCartItem> getByUserId(int userId) {
        return saveCartItemRepository.findByUserId(userId);
    }

    @Transactional
    public void save(@NotNull SaveCartItem saveCartItem) {
        if (saveCartItem.getId() != null) {
            throw new IllegalArgumentException("New Cart id must not be null");
        }
        saveCartItemRepository.save(saveCartItem);
    }

    @Transactional
    public void deleteByUserId(int userId) {
        saveCartItemRepository.deleteById(userId);
    }
}