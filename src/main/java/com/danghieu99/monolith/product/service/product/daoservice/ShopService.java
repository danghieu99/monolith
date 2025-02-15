package com.danghieu99.monolith.product.service.product.daoservice;

import com.danghieu99.monolith.common.exception.ResourceNotFoundException;
import com.danghieu99.monolith.product.entity.Shop;
import com.danghieu99.monolith.product.repository.jpa.ShopRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShopService {

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

    public Shop getByUUID(String uuid) {
        return repository.findByUuid(UUID.fromString(uuid)).orElseThrow(() -> new ResourceNotFoundException("Shop", "UUID", uuid));
    }

    public Shop getByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Shop", "name", name));
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
