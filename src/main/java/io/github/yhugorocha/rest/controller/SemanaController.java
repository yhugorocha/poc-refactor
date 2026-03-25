package io.github.yhugorocha.rest.controller;

import io.github.yhugorocha.domain.entity.Semana;
import io.github.yhugorocha.domain.repositorio.Semanas;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/agenda/semana/")
public class SemanaController {

    Semanas semanas;

    public SemanaController(Semanas semanas){
        this.semanas = semanas;
    }

    @GetMapping("{id}")
    public Semana findByIdSemana(@PathVariable Integer id){
        return semanas.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Semana não encontrada"));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Semana salvar( @RequestBody Semana semana){
        return semanas.save(semana);

    }

    @GetMapping
    public List<Semana> find(Semana filtro ){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING );

        Example example = Example.of(filtro, matcher);
        return semanas.findAll(example);
    }
}
