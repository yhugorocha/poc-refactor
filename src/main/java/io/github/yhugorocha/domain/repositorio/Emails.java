package io.github.yhugorocha.domain.repositorio;

import io.github.yhugorocha.domain.entity.Email;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Emails extends JpaRepository<Email, Long> {
}
