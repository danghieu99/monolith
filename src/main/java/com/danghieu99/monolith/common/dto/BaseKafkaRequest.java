package com.danghieu99.monolith.common.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BaseKafkaRequest extends BaseRequest {

    @NotBlank
    private String systemCode;
}
