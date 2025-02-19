package com.danghieu99.monolith.product.service.dao;

import com.danghieu99.monolith.common.exception.ResourceNotFoundException;
import com.danghieu99.monolith.product.entity.Shop;
import com.danghieu99.monolith.product.repository.jpa.ShopRepository;
import com.danghieu99.monolith.product.repository.jpa.join.ProductShopRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
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
    private final ProductDaoService productDaoService;

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
    public Shop updateById(@NotNull int id, @NotNull Shop shop) {
        return repository.save(shop);
    }

    public Shop getById(@NotNull int id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Shop", "id", id));
    }

    public Shop getByUUID(@NotNull UUID uuid) {
        return repository.findByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("Shop", "UUID", uuid));
    }

    public Shop getByName(@NotBlank String name) {
        return repository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Shop", "name", name));
    }

    public Page<Shop> getByNameContaining(@NotNull String name, @NotNull Pageable pageable) {
        return repository.findByNameContaining(name, pageable);
    }

    public Shop getByAccountId(@NotNull int accountId) {
        return repository.findByAccountId(accountId)
                .orElseThrow(() -> new ResourceNotFoundException("Shop", "accountId", accountId));
    }

    @Transactional
    public void deleteById(@NotNull int id) {
        repository.deleteById(id);
        productDaoService.deleteByShopId(id);
    }

    @Transactional
    public void deleteByUUID(@NotNull String uuid) {
        repository.deleteByUuid(UUID.fromString(uuid));
        productDaoService.deleteByShopUUID(UUID.fromString(uuid));
    }
}