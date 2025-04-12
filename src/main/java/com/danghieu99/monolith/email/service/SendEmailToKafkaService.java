package com.danghieu99.monolith.email.service;

import com.danghieu99.monolith.email.dto.SendEmailRequest;
import com.danghieu99.monolith.email.dto.kafka.SendEmailKafkaRequest;
import com.danghieu99.monolith.email.dto.kafka.SendEmailKafkaRequestAttachment;
import com.danghieu99.monolith.email.kafka.SendEmailKafkaProducer;
import com.danghieu99.monolith.email.mapper.EmailMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SendEmailToKafkaService {

    private final SendEmailKafkaProducer producer;
    private final EmailMapper emailMapper;

    @Async
    @Transactional
    public void sendToKafka(SendEmailRequest request) {
        SendEmailKafkaRequest kafkaRequest = emailMapper.toSendEmailKafkaRequest(request);
        kafkaRequest.setAttachments(request.getFiles().stream().map(file -> {
            return SendEmailKafkaRequestAttachment.builder()
                    .fileName(file.getName())
                    .contentType(file.getContentType())
                    .fileUrl("")
                    .build();
        }).toList());
        producer.send(kafkaRequest);
    }

    private List<String> uploadFile(MultipartFile file) {
        return null;
    }
}
