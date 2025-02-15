package com.danghieu99.monolith.product.service.product.daoservice;

import com.danghieu99.monolith.common.exception.ResourceNotFoundException;
import com.danghieu99.monolith.product.entity.Variant;
import com.danghieu99.monolith.product.repository.jpa.VariantRepository;
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
public class VariantService {

    private final VariantRepository variantRepository;

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

    public Variant updateByUUID(UUID uuid, Variant variant) {
        var current = getByUUID(uuid);
        if (variant.getPrice() != null) {
            current.setPrice(variant.getPrice());
        }
        if (variant.getStock() != null) {
            current.setStock(variant.getStock());
        }
        return variantRepository.save(variant);
    }

    @Transactional
    public void deleteByUUID(UUID uuid) {
        variantRepository.deleteByUuid(uuid);
    }
}
