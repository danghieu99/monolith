package com.danghieu99.monolith.product.service.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminCategoryService {

    private final CategoryCrudService categoryService;


}
