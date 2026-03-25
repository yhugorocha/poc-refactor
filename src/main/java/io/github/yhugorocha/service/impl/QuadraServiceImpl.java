package io.github.yhugorocha.service.impl;

import io.github.yhugorocha.domain.entity.Endereco;
import io.github.yhugorocha.domain.entity.Quadra;
import io.github.yhugorocha.domain.entity.Regiao;
import io.github.yhugorocha.domain.repositorio.Enderecos;
import io.github.yhugorocha.domain.repositorio.Quadras;
import io.github.yhugorocha.domain.repositorio.Regioes;
import io.github.yhugorocha.exception.RegraNegocioException;
import io.github.yhugorocha.rest.dto.QuadraDTO;
import io.github.yhugorocha.service.QuadraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class QuadraServiceImpl implements QuadraService {

    @Autowired
    Quadras quadras;
    @Autowired
    Regioes regioes;
    @Autowired
    Enderecos enderecos;

    @Override
    @Transactional
    public Quadra salvar(QuadraDTO quadraDTO) {

        Regiao regiao = regioes.findById(quadraDTO.getRegiao())
                .orElseThrow(() -> new RegraNegocioException("Região Não Encontrada"));

        Endereco endereco = enderecos.save(quadraDTO.getEndereco());

        Quadra quadra = new Quadra();
        quadra.setNome(quadraDTO.getNome());
        quadra.setQtd_pessoas(quadraDTO.getQtd_pessoas());
        quadra.setFoto(quadraDTO.getFoto());
        quadra.setRegiao(regiao);
        quadra.setEndereco(endereco);

        return quadras.save(quadra);
    }

    @Override
    @Transactional
    public Quadra findByid(Integer id) {
        return quadras.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quadra não encontrada"));
    }

    @Override
    @Transactional
    public void delete(Integer id) {
        quadras.findById(id).map(quadra -> {
            quadras.delete(quadra);
            enderecos.delete(quadra.getEndereco());
            return quadra;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quadra não encontrada"));

    }

    @Override
    @Transactional
    public Quadra update(Integer id, Quadra quadra) {

         quadras.findById(id).map(qdExistente ->{
            quadra.setId(qdExistente.getId());
            enderecos.save(quadra.getEndereco());
            quadras.save(quadra);
            return qdExistente;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quadra não encontrada"));
         return quadra;
    }

    @Override
    public List<Quadra> findAll(Example example) {
        return quadras.findAll();
    }

    @Override
    public List<Quadra> obterQuadraPorRegiao(Integer id) {
        return quadras.quadraPorRegiao(id);
    }


}
