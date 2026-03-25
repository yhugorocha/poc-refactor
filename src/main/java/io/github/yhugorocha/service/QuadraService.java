package io.github.yhugorocha.service;

import io.github.yhugorocha.domain.entity.Quadra;
import io.github.yhugorocha.rest.dto.QuadraDTO;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface QuadraService {

    Quadra salvar(QuadraDTO quadraDTO);

    Quadra findByid(Integer id);

    void delete(Integer id);

    Quadra update(Integer id, Quadra quadra);

    List<Quadra> findAll(Example example);

    List<Quadra> obterQuadraPorRegiao(Integer id);
}
