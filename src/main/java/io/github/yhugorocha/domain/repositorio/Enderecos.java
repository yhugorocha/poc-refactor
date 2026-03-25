package io.github.yhugorocha.domain.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import io.github.yhugorocha.domain.entity.Endereco;

public interface Enderecos extends JpaRepository<Endereco,Integer> {

}
