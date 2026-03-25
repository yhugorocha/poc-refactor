package io.github.yhugorocha.domain.repositorio;

import io.github.yhugorocha.domain.entity.Horario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Horarios extends JpaRepository<Horario, Integer> {
}
