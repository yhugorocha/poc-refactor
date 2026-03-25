package io.github.yhugorocha.domain.repositorio;

import io.github.yhugorocha.domain.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Usuarios extends JpaRepository<Usuario,Integer> {

    Optional<Usuario> findByLogin(String login);
}
