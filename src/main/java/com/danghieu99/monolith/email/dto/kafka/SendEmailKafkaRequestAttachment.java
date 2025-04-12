package com.danghieu99.monolith.email.dto.kafka;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class SendEmailKafkaRequestAttachment {

    @NotBlank
    private final String fileName;

    @NotBlank
    private final String fileUrl;

    @NotBlank
    private final String contentType;

}
