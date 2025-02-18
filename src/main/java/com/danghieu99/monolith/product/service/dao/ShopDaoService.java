package com.danghieu99.monolith.product.service.dao;

import com.danghieu99.monolith.common.exception.ResourceNotFoundException;
import com.danghieu99.monolith.product.entity.Shop;
import com.danghieu99.monolith.product.repository.jpa.ShopRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShopDaoService {

    private final ShopRepository repository;

    public List<Shop> getAll() {
        return repository.findAll();
    }

    @Transactional
    public Shop save(Shop shop) {
        if (shop.getId() != null) {
            throw new IllegalArgumentException("New shop id must be null");
        }
        return repository.save(shop);
    }

    @Transactional
    public Collection<Shop> saveAll(Collection<Shop> shops) {
        return repository.saveAll(shops);
    }

    @Transactional
    public Shop update(Shop shop) {
        if (shop.getId() == null) {
            throw new IllegalArgumentException("Update shop id must not be null");
        }
        return repository.save(shop);
    }

    public Shop getById(int id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Shop", "id", id));
    }

    public Shop getByUUID(UUID uuid) {
        return repository.findByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("Shop", "UUID", uuid));
    }

    public Shop getByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Shop", "name", name));
    }

    public Page<Shop> getByNameContaining(@NotNull String name, @NotNull Pageable pageable) {
        return repository.findByNameContaining(name, pageable);
    }

    @Transactional
    public void deleteById(int id) {
        repository.deleteById(id);
    }

    @Transactional
    public void deleteByUUID(UUID uuid) {
        repository.deleteByUuid(uuid);
    }
}
