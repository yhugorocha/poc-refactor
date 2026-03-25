package io.github.yhugorocha.domain.repositorio;

import io.github.yhugorocha.domain.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Autenticacoes extends JpaRepository<Usuario, Integer> {
}
