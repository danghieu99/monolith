package com.danghieu99.monolith.product.service.dao;

import com.danghieu99.monolith.common.exception.ResourceNotFoundException;
import com.danghieu99.monolith.product.entity.Variant;
import com.danghieu99.monolith.product.repository.jpa.VariantRepository;
import com.danghieu99.monolith.product.repository.jpa.join.VariantAttributeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VariantDaoService {

    private final VariantRepository variantRepository;
    private final VariantAttributeRepository variantAttributeRepository;

    @Transactional
    public Variant save(Variant variant) {
        return variantRepository.save(variant);
    }

    public Variant getByUUID(UUID uuid) {
        return variantRepository.findByUuid(uuid).orElseThrow(() -> new ResourceNotFoundException("Variant", "uuid", uuid));
    }

    public Page<Variant> getByProductUUID(UUID uuid, Pageable pageable) {
        return variantRepository.findByProductUuid(uuid, pageable);
    }

    @Transactional
    public Collection<Variant> saveAll(Set<Variant> variants) {
        return variantRepository.saveAll(variants);
    }

    @Transactional
    public void updateByUUID(UUID uuid, Variant variant) {
        var current = getByUUID(uuid);
        if (variant.getPrice() != null) {
            current.setPrice(variant.getPrice());
        }
        if (variant.getStock() != null) {
            current.setStock(variant.getStock());
        }
        variantRepository.save(variant);
    }

    @Transactional
    public void deleteByUUIDCascading(UUID uuid) {
        variantRepository.deleteByUuid(uuid);
        variantAttributeRepository.deleteByVariantUUID(uuid);
    }

    @Transactional
    public void deleteByProductIdCascading(int productId) {
        variantRepository.deleteByProductId(productId);
        variantAttributeRepository.deleteByProductId(productId);
    }

    @Transactional
    public void deleteByProductUUIDCascading(UUID uuid) {
        variantRepository.deleteByProductUUID(uuid);
        variantAttributeRepository.deleteByProductUUID(uuid);
    }
}
