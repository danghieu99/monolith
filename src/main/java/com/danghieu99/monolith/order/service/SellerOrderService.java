package com.danghieu99.monolith.order.service;

import com.danghieu99.monolith.order.dto.response.OrderDetailsResponse;
import com.danghieu99.monolith.order.mapper.OrderMapper;
import com.danghieu99.monolith.order.repository.OrderRepository;
import com.danghieu99.monolith.security.config.auth.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellerOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public Page<OrderDetailsResponse> getByCurrentShopAccount(UserDetailsImpl userDetails,
                                                              Pageable pageable) {
        var orders = orderRepository.findByShopUUID(userDetails.getUuid(), pageable);
        return orders.map(orderMapper::toOrderDetailsResponse);
    }


}