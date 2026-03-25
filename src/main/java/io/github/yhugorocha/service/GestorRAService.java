package io.github.yhugorocha.service;

import io.github.yhugorocha.domain.entity.GestorRA;
import io.github.yhugorocha.rest.dto.GestorRADTO;
import org.springframework.stereotype.Service;

@Service
public interface GestorRAService {
    GestorRA salvar(GestorRADTO dto);
    GestorRA update(Integer id, GestorRA gestor);
}
