package com.danghieu99.monolith.product.service.category;

import com.danghieu99.monolith.product.dto.response.GetCategoryResponse;
import com.danghieu99.monolith.product.mapper.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCategoryService {

    private final CategoryCrudService categoryCrudService;
    private final CategoryMapper categoryMapper;

    public GetCategoryResponse getCategoryByName(@Length(min = 3) String name) {
        return categoryMapper.toCategoryDto(categoryCrudService.getByName(name));
    }
}
