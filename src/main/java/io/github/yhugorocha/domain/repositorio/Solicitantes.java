package io.github.yhugorocha.domain.repositorio;

import io.github.yhugorocha.domain.entity.Solicitante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Solicitantes extends JpaRepository<Solicitante, Integer> {

}
