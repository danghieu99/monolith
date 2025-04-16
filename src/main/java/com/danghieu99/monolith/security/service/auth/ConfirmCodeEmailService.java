package com.danghieu99.monolith.security.service.auth;

import com.danghieu99.monolith.security.entity.redis.ConfirmCode;
import com.danghieu99.monolith.security.repository.redis.ConfirmCodeRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class ConfirmCodeEmailService {

    private final ConfirmCodeRepository confirmCodeRepository;

    @Value("${confirm.code.email.expiration.minutes}")
    private long confirmCodeExpirationMinutes;

    @Value("${confirm.code.email.type}")
    private String confirmCodeType;

    @Async
    @Transactional
    public void create(@NotBlank final String accountUUID,
                       String code) {
        ConfirmCode confirmCode = ConfirmCode.builder()
                .accountUUID(accountUUID)
                .value(code)
                .type(confirmCodeType)
                .expiration(confirmCodeExpirationMinutes)
                .build();
        confirmCodeRepository.save(confirmCode);
    }

    @Transactional
    public String validate(@NotBlank final String code) {
        Optional<ConfirmCode> current = confirmCodeRepository.findByValue(code);
        boolean valid = current.isPresent() && current.get().getValue().equals(code);
        if (valid) {
            confirmCodeRepository.delete(current.get());
            return current.get().getAccountUUID();
        } else {
            throw new IllegalStateException("Invalid confirmation code");
        }
    }
}