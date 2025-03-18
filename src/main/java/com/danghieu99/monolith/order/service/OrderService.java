package com.danghieu99.monolith.order.service;

import com.danghieu99.monolith.common.exception.ResourceNotFoundException;
import com.danghieu99.monolith.order.constant.EOrderStatus;
import com.danghieu99.monolith.order.dto.request.PlaceOrderRequest;
import com.danghieu99.monolith.order.dto.response.OrderDetailsResponse;
import com.danghieu99.monolith.order.dto.response.OrderItemResponse;
import com.danghieu99.monolith.order.dto.response.PlaceOrderResponse;
import com.danghieu99.monolith.order.entity.Item;
import com.danghieu99.monolith.order.entity.Order;
import com.danghieu99.monolith.order.repository.ItemRepository;
import com.danghieu99.monolith.order.repository.OrderRepository;
import com.danghieu99.monolith.product.entity.Product;
import com.danghieu99.monolith.product.entity.Shop;
import com.danghieu99.monolith.product.entity.Variant;
import com.danghieu99.monolith.product.repository.jpa.ProductRepository;
import com.danghieu99.monolith.product.repository.jpa.ShopRepository;
import com.danghieu99.monolith.product.repository.jpa.VariantRepository;
import com.danghieu99.monolith.security.entity.Account;
import com.danghieu99.monolith.security.repository.jpa.AccountRepository;
import com.danghieu99.monolith.security.service.auth.AuthenticationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final AuthenticationService authenticationService;
    private final ShopRepository shopRepository;
    private final ItemRepository itemRepository;
    private final ProductRepository productRepository;
    private final VariantRepository variantRepository;
    private final AccountRepository accountRepository;

    public OrderDetailsResponse getOrderDetailsByUUID(String orderUUID) {
        Order order = orderRepository.findByUuid(UUID.fromString(orderUUID))
                .orElseThrow(() -> new ResourceNotFoundException("Order", "uuid", orderUUID));
        Shop orderShop = shopRepository.findById(order.getShopId())
                .orElseThrow(() -> new ResourceNotFoundException("Shop", "id", order.getShopId()));
        List<OrderItemResponse> orderItems = itemRepository.findByOrderId(order.getId())
                .stream().map(item -> {
                    Product itemProduct = productRepository.findById(item.getProductId())
                            .orElseThrow(() -> new ResourceNotFoundException("Product", "id", item.getProductId()));
                    Variant itemVariant = variantRepository.findById(item.getVariantId())
                            .orElseThrow(() -> new ResourceNotFoundException("Variant", "id", item.getVariantId()));
                    return OrderItemResponse.builder()
                            .productUUID(itemProduct.getUuid().toString())
                            .variantUUID(itemVariant.getUuid().toString())
                            .quantity(item.getQuantity())
                            .build();
                })
                .toList();

        return OrderDetailsResponse.builder()
                .shopUUID(orderShop.getUuid().toString())
                .items(orderItems)
                .orderStatus(order.getStatus().toString())
                .build();
    }

    public List<String> getByCurrentUser() {
        Account current = accountRepository.findById(authenticationService.getCurrentUserDetails().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Account", "id", authenticationService.getCurrentUserDetails().getId()));
        return getOrderUUIDSbyUserUUID(current.getUuid().toString());
    }

    public List<String> getOrderUUIDSbyUserUUID(String userUUID) {
        return orderRepository.findByUserUUID(UUID.fromString(userUUID)).stream()
                .map(order -> order.getUuid().toString())
                .collect(Collectors.toList());
    }

    @Transactional
    public PlaceOrderResponse placeOrder(@RequestBody List<PlaceOrderRequest> requests) {
        List<Item> items = new ArrayList<>();
        requests.forEach(orderRequest -> {
            Order newOrder = Order.builder()
                    .userId(authenticationService.getCurrentUserDetails().getId())
                    .shopId(shopRepository.findByUuid(UUID.fromString(orderRequest.getShopUUID()))
                            .orElseThrow(() -> new ResourceNotFoundException("Shop", "uuid", orderRequest.getShopUUID()))
                            .getId())
                    .build();
            Order savedOrder = orderRepository.saveAndFlush(newOrder);
            orderRequest.getItems().forEach(requestItem -> {
                Product itemProduct = productRepository.findByUuid(UUID.fromString(requestItem.getProductUUID()))
                        .orElseThrow(() -> new ResourceNotFoundException("Product", "uuid", requestItem.getProductUUID()));
                Variant itemVariant = variantRepository.findByUuid(UUID.fromString(requestItem.getVariantUUID()))
                        .orElseThrow(() -> new ResourceNotFoundException("Variant", "uuid", requestItem.getVariantUUID()));
                Item newItem = Item.builder()
                        .orderId(savedOrder.getId())
                        .productId(itemProduct.getId())
                        .variantId(itemVariant.getId())
                        .quantity(requestItem.getQuantity())
                        .build();
                items.add(newItem);
            });
        });
        itemRepository.saveAll(items);

        return PlaceOrderResponse.builder()
                .success(true)
                .message("Place order(s) success!")
                .build();
    }

    @Transactional
    public void updateOrderStatus(String orderUUID, String status) {
        EOrderStatus orderStatus = EOrderStatus.valueOf(status);
        orderRepository.updateOrderStatus(UUID.fromString(orderUUID), orderStatus);
    }

    @Transactional
    public void cancelOrder(String orderUUID) {
        Order savedOrder = orderRepository.findByUuid(UUID.fromString(orderUUID))
                .orElseThrow(() -> new ResourceNotFoundException("Order", "uuid", orderUUID));
        savedOrder.setStatus(EOrderStatus.ORDER_CANCELLED);
        orderRepository.save(savedOrder);
    }
}