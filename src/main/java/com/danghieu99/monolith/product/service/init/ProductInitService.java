package com.danghieu99.monolith.product.service.init;

import com.danghieu99.monolith.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductInitService {

    private final ProductService productService;


}
