package com.danghieu99.monolith.product.service.dao;

import com.danghieu99.monolith.product.repository.jpa.VariantAttributeRepository;
import com.danghieu99.monolith.product.entity.VariantAttribute;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class VariantAttributeDaoService {

    private final VariantAttributeRepository variantAttributeRepository;

    @Transactional
    public VariantAttribute save(VariantAttribute variantAttribute) {
        return variantAttributeRepository.save(variantAttribute);
    }

    @Transactional
    public Collection<VariantAttribute> saveAll(Collection<VariantAttribute> variantAttributes) {
        return variantAttributeRepository.saveAll(variantAttributes);
    }
}
