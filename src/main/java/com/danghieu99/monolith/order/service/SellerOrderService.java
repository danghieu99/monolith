package com.danghieu99.monolith.order.service;

import com.danghieu99.monolith.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerOrderService {

    private final OrderRepository orderRepository;

}