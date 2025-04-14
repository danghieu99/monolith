package com.danghieu99.monolith.cart.dto;

import com.danghieu99.monolith.common.dto.BaseRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class GetCartResponse extends BaseRequest {

    //variantUUID:quantity
    @NotEmpty
    private Map<@NotBlank String, @NotNull Integer> items;
}
