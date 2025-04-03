package com.danghieu99.monolith.order.dto.request.kafka;

import com.danghieu99.monolith.common.dto.BaseKafkaRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CancelOrderKafkaRequest extends BaseKafkaRequest {

    @NotBlank
    private final String orderUUID;

    @NotBlank
    private final String reason;

    @NotBlank
    private String accountUUID;
}