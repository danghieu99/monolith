package com.danghieu99.monolith.order.controller;

import com.danghieu99.monolith.order.dto.request.CancelOrderRequest;
import com.danghieu99.monolith.order.dto.request.PlaceOrderRequest;
import com.danghieu99.monolith.order.dto.response.PlaceOrderResponse;
import com.danghieu99.monolith.order.service.UserOrderService;
import com.danghieu99.monolith.security.config.auth.UserDetailsImpl;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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

    @GetMapping("/my-orders")
    public List<String> getByCurrentUser(@AuthenticationPrincipal @NotNull UserDetailsImpl userDetails) {
        return userOrderService.getOrderUUIDsByCurrentUser(userDetails);
    }

    @PostMapping("")
    @Transactional
    public PlaceOrderResponse placeOrder(@RequestBody PlaceOrderRequest request,
                                         @AuthenticationPrincipal @NotNull UserDetailsImpl userDetails) {
        userOrderService.placeOrder(request, userDetails);
        return PlaceOrderResponse.builder()
                .success(true)
                .message("Place order success!")
                .build();
    }

    @PatchMapping("")
    @Transactional
    public void cancelOrder(@RequestParam CancelOrderRequest request,
                            @AuthenticationPrincipal @NotNull UserDetailsImpl userDetails) {
        userOrderService.cancelOrder(request, userDetails);
    }
}
