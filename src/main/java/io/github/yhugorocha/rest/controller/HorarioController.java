package io.github.yhugorocha.rest.controller;

import io.github.yhugorocha.domain.entity.Horario;
import io.github.yhugorocha.domain.repositorio.Horarios;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/agenda/horario/")
public class HorarioController {

    Horarios horarios;

    public HorarioController(Horarios horarios){
        this.horarios = horarios;
    }

    @GetMapping("{id}")
    public Horario findByIdSemana(@PathVariable Integer id){
        return horarios.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Horario não encontrado"));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Horario salvar( @RequestBody Horario horario){
        return horarios.save(horario);

    }

    @GetMapping
    public List<Horario> find(Horario filtro ){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING );

        Example example = Example.of(filtro, matcher);
        return horarios.findAll(example);
    }
}
