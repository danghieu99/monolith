package com.danghieu99.monolith.product.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserProductService {

    private final ProductCrudService productCrudService;



}
