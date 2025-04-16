package com.danghieu99.monolith.order.dto.request.kafka;

import com.danghieu99.monolith.common.dto.BaseKafkaRequest;
import com.danghieu99.monolith.order.dto.request.OrderAddressRequest;
import com.danghieu99.monolith.order.dto.request.OrderItemRequest;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Getter
@Setter
public class PlaceOrderKafkaRequest extends BaseKafkaRequest {

    @NotEmpty
    private String shopUUID;

    @NotNull
    private OrderAddressRequest address;

    @NotNull
    private String shipmentProviderUUID;

    @NotEmpty
    private List<OrderItemRequest> items;

    @NotEmpty
    private String accountUUID;
}
