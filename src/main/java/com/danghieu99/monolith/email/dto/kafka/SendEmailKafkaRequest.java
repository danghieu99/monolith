package com.danghieu99.monolith.email.dto.kafka;

import com.danghieu99.monolith.common.dto.BaseKafkaRequest;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class SendEmailKafkaRequest extends BaseKafkaRequest {

    private String[] from;

    private String[] to;

    private String[] cc;

    private String[] bcc;

    private String subject;

    private String plainText;

    private String html;

    private String templateName;

    private Map<String, String> templateParams;

    private List<SendEmailKafkaRequestAttachment> attachments;
}