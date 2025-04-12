package com.danghieu99.monolith.email.service;

import com.danghieu99.monolith.email.dto.kafka.SendEmailKafkaRequest;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class SendEmailService {

    private final JavaMailSender mailSender;
    private final TemplateEngine templateEngine;
    @Value("${spring.mail.from}")
    private String from;

    @Async
    @Transactional
    public void send(SendEmailKafkaRequest request) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = this.prepareMessage(mimeMessage, request, from);
            String content = request.getPlainText();
            String htmlContent = request.getHtml();
            if (request.getTemplateName() != null && !request.getTemplateName().isBlank()) {
                Context ctx = this.prepareContext(request);
                String processedContent = templateEngine.process(request.getTemplateName(), ctx);
                helper.setText(processedContent, true);
            } else if (htmlContent != null && !htmlContent.isBlank()) {
                if (content != null && !content.isBlank()) {
                    helper.setText(content, htmlContent);
                } else {
                    helper.setText(htmlContent, true);
                }
            }
            if (request.getAttachments() != null && !request.getAttachments().isEmpty()) {
                this.prepareAttachments(helper, request);
            }
            mailSender.send(mimeMessage);
            log.info("Send mail success to {}", Arrays.toString(request.getTo()));
        } catch (MessagingException e) {
            log.error("Send mail error {}", e.toString());
        } catch (Exception e) {
            log.error("Unexpected send mail error {}", e.toString());
        }
    }

    private Context prepareContext(SendEmailKafkaRequest request) {
        Context ctx = new Context();
        if (Objects.nonNull(request.getTemplateParams())) {
            Set<String> keySet = request.getTemplateParams().keySet();
            keySet.forEach(s -> {
                ctx.setVariable(s, request.getTemplateParams().get(s));
            });
        }
        return ctx;
    }

    @SneakyThrows
    private MimeMessageHelper prepareMessage(MimeMessage mimeMessage, SendEmailKafkaRequest request, String from) {
        MimeMessageHelper message = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
        message.setSubject(request.getSubject());
        message.setFrom(from);
        message.setTo(request.getTo());
        if (request.getCc() != null && request.getCc().length > 0) {
            message.setCc(request.getCc());
        }
        if (request.getBcc() != null && request.getBcc().length > 0) {
            message.setBcc(request.getBcc());
        }
        return message;
    }

    @Transactional
    protected void prepareAttachments(MimeMessageHelper helper, SendEmailKafkaRequest request) {
        request.getAttachments().forEach((attachment) -> {
            try (InputStream inputStream = URI.create(attachment.getFileUrl()).toURL().openStream()) {
                helper.addAttachment(attachment.getFileName(),
                        new ByteArrayResource(inputStream.readAllBytes()),
                        attachment.getContentType());
            } catch (IOException | MessagingException e) {
                log.error("Attachment error {}", e.toString());
            }
        });
    }
}
