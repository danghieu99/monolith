package com.danghieu99.monolith.order.controller;

import com.danghieu99.monolith.order.dto.request.CancelOrderRequest;
import com.danghieu99.monolith.order.dto.request.PlaceOrderRequest;
import com.danghieu99.monolith.order.dto.response.OrderDetailsResponse;
import com.danghieu99.monolith.order.dto.response.PlaceOrderResponse;
import com.danghieu99.monolith.order.service.UserOrderService;
import com.danghieu99.monolith.security.config.auth.UserDetailsImpl;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/order")
@RequiredArgsConstructor
@Validated
public class UserOrderController {

    private final UserOrderService userOrderService;

    @GetMapping("/")
    public List<OrderDetailsResponse> getAllByCurrentUser(@AuthenticationPrincipal @NotNull UserDetailsImpl userDetails) {
        return userOrderService.getAllByCurrentUser(userDetails);
    }

    @PostMapping("/place")
    @Transactional
    public PlaceOrderResponse place(@RequestBody PlaceOrderRequest request,
                                    @AuthenticationPrincipal @NotNull UserDetailsImpl userDetails) {
        userOrderService.place(request, userDetails);
        return PlaceOrderResponse.builder()
                .success(true)
                .message("Place order success!")
                .build();
    }

    @PostMapping("/cancel")
    @Transactional
    public void cancel(@RequestParam CancelOrderRequest request,
                       @AuthenticationPrincipal @NotNull UserDetailsImpl userDetails) {
        userOrderService.cancel(request, userDetails);
    }
}