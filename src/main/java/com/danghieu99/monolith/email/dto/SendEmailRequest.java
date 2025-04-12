package com.danghieu99.monolith.email.dto;

import com.danghieu99.monolith.common.dto.BaseRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
public class SendEmailRequest extends BaseRequest {

    private String systemCode;

    @NotBlank
    private String from;

    @Valid
    private List<@NotBlank @Email String> to;

    @Valid
    private List<@NotBlank @Email String> cc;

    @NotBlank
    @Valid
    private List<@NotBlank @Email String> bcc;

    private String subject;

    private String plainText;

    private String html;

    private String templateName;

    @Size(min = 1)
    private Map<@NotBlank String, @NotBlank String> paramMap;

    @Size(min = 1, max = 3)
    private List<MultipartFile> files;
}