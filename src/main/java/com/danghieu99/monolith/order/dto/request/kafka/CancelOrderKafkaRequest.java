package com.danghieu99.monolith.order.dto.request.kafka;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class CancelOrderKafkaRequest {

    @NotBlank
    private final String orderUUID;

    @NotBlank
    private final String reason;

    @NotBlank
    private String accountUUID;
}