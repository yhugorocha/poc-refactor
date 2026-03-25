package io.github.yhugorocha.rest.controller;

import io.github.yhugorocha.domain.entity.Regiao;
import io.github.yhugorocha.domain.repositorio.Regioes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/agenda/regiao/")
public class RegiaoController {

    Regioes regioes;

    public RegiaoController(Regioes regioes){
        this.regioes = regioes;
    }

    @GetMapping("{id}")
    public Regiao findByIdRegioes(@PathVariable Integer id){
        return regioes.findById(id)
                      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Região não encontrada"));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Regiao salvar( @RequestBody Regiao regiao){
        return regioes.save(regiao);

    }

    @GetMapping
    public List<Regiao> findAll(){
        return regioes.findAll();
    }

}
