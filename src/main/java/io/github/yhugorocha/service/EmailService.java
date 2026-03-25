package io.github.yhugorocha.service;

import io.github.yhugorocha.domain.entity.Email;
import io.github.yhugorocha.rest.dto.EmailDto;
import io.github.yhugorocha.service.impl.EmailServiceImpl;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    EmailServiceImpl sendEmail(Email email);
}
