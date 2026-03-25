package io.github.yhugorocha.service.impl;

import io.github.yhugorocha.domain.entity.GestorRA;
import io.github.yhugorocha.domain.entity.Regiao;
import io.github.yhugorocha.domain.repositorio.GestoresRA;
import io.github.yhugorocha.domain.repositorio.Regioes;
import io.github.yhugorocha.exception.RegraNegocioException;
import io.github.yhugorocha.rest.dto.GestorRADTO;
import io.github.yhugorocha.service.GestorRAService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class GestorRAServiceImpl implements GestorRAService {

    private final GestoresRA repository;
    private final Regioes repositoryRegiao;

    @Override
    @Transactional
    public GestorRA salvar(GestorRADTO dto) {

        Integer idRegiao = dto.getRegiao();
        Regiao regiao = repositoryRegiao.
                findById(idRegiao).
                orElseThrow(() -> new RegraNegocioException("Código de Região inválido"));

        GestorRA gestor = new GestorRA();
        gestor.setCpf(dto.getCpf());
        gestor.setNome(dto.getNome());
        gestor.setEmail(dto.getEmail());
        gestor.setTelefone(dto.getTelefone());
        gestor.setRegiao(regiao);

        return repository.save(gestor);
    }

    @Override
    public GestorRA update(Integer id, GestorRA gestor) {

        repository.findById(id).map(gtExistente ->{
            gestor.setId(gtExistente.getId());
            repository.save(gestor);
            return gtExistente;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Gestor não encontrado"));
        return gestor;
    }
}
