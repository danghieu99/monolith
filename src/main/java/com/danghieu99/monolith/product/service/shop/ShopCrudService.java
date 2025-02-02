package com.danghieu99.monolith.product.service.shop;

import com.danghieu99.monolith.common.exception.ResourceNotFoundException;
import com.danghieu99.monolith.product.entity.Shop;
import com.danghieu99.monolith.product.repository.jpa.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShopCrudService {

    private final ShopRepository repository;

    public List<Shop> getAll() {
        return repository.findAll();
    }

    public Shop create(Shop shop) {
        if (shop.getId() != null) {
            throw new IllegalArgumentException("New shop id must be null");
        }
        return repository.save(shop);
    }

    public Shop update(Shop shop) {
        if (shop.getId() == null) {
            throw new IllegalArgumentException("Update shop id must not be null");
        }
        return repository.save(shop);
    }

    public Shop getById(int id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Shop", "id", id));
    }

    public Shop getByUUID(String uuid) {
        return repository.findByUuid(UUID.fromString(uuid)).orElseThrow(() -> new ResourceNotFoundException("Shop", "UUID", uuid));
    }

    public Shop getByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Shop", "name", name));
    }

    public void delete(int id) {
        repository.deleteById(id);
    }
}
