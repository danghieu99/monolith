package com.danghieu99.monolith.product.service.dao;

import com.danghieu99.monolith.common.exception.ResourceNotFoundException;
import com.danghieu99.monolith.product.repository.jpa.AttributeRepository;
import com.danghieu99.monolith.product.entity.Attribute;
import com.danghieu99.monolith.product.repository.jpa.join.VariantAttributeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttributeDaoService {

    private final AttributeRepository attributeRepository;
    private final VariantAttributeRepository variantAttributeRepository;

    @Transactional
    public Attribute save(Attribute attribute) {
        return attributeRepository.save(attribute);
    }

    @Transactional
    public Collection<Attribute> saveAll(Collection<Attribute> attributes) {
        return attributeRepository.saveAll(attributes);
    }

    public Attribute getById(int id) {
        return attributeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Attribute", "id", id));
    }

    public List<Attribute> getByUUID(UUID productUUID) {
        return attributeRepository.findByProductUUID(productUUID);
    }

    @Transactional
    public void deleteById(int id) {
        attributeRepository.deleteById(id);
        variantAttributeRepository.deleteByAttributeId(id);
    }
}
