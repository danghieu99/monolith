package com.danghieu99.monolith.order.dto.request.kafka;

import com.danghieu99.monolith.common.dto.BaseKafkaRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Builder
@Getter
@Setter
public class CancelOrderKafkaRequest extends BaseKafkaRequest {

    @NotBlank
    private String orderUUID;

    @NotBlank
    private String reason;

    @NotBlank
    private String accountUUID;
}