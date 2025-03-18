package com.danghieu99.monolith.order.dto.response;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderDetailsResponse {

    @NotEmpty
    private String shopUUID;

    @NotEmpty
    private String userUUID;

    @NotEmpty
    private String orderStatus;

    @NotEmpty
    private List<OrderItemResponse> items;
}
