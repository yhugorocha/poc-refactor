package io.github.yhugorocha.rest.controller;

import io.github.yhugorocha.domain.entity.Quadra;
import io.github.yhugorocha.rest.dto.QuadraDTO;
import io.github.yhugorocha.service.QuadraService;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/agenda/quadra/")
public class QuadraController {

    QuadraService quadraService;

    public QuadraController(QuadraService quadraService){
        this.quadraService = quadraService;
    }

    @GetMapping("{id}")
    public Quadra findByIdQuadra(@PathVariable Integer id){
        return quadraService.findByid(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Quadra save(@RequestBody @Valid QuadraDTO quadraDTO){

        return quadraService.salvar(quadraDTO);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletebyId(@PathVariable Integer id){
        quadraService.delete(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Quadra update (@PathVariable Integer id, @Valid @RequestBody Quadra quadra){

        return quadraService.update(id,quadra);
    }
    @GetMapping
    public List<Quadra> find(Quadra filtro ){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(
                        ExampleMatcher.StringMatcher.CONTAINING );

        Example example = Example.of(filtro, matcher);
        return quadraService.findAll(example);
    }

    @GetMapping("regiao/{id}")
    public List<Quadra> findQuadraRegiao(@PathVariable Integer id){
        return quadraService.obterQuadraPorRegiao(id);
    }

}
