package com.danghieu99.monolith.product.service.product;

import com.danghieu99.monolith.product.entity.Variant;
import com.danghieu99.monolith.product.repository.jpa.VariantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class VariantCrudService {

    private final VariantRepository variantRepository;

    @Transactional
    public Collection<Variant> saveAll(Set<Variant> variants) {
        return variantRepository.saveAll(variants);
    }
}
