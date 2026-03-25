package io.github.yhugorocha.rest.controller;

import io.github.yhugorocha.domain.entity.Endereco;
import io.github.yhugorocha.domain.entity.Solicitante;
import io.github.yhugorocha.domain.repositorio.Enderecos;
import io.github.yhugorocha.domain.repositorio.Solicitantes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/agenda/solicitante/")
public class SolicitanteController {

    private Solicitantes solicitantes;
    private Enderecos enderecos;

    public SolicitanteController(Solicitantes solicitantes, Enderecos enderecos){
        this.solicitantes = solicitantes;
        this.enderecos = enderecos;
    }

    @GetMapping("{id}")
    public Solicitante getSolicitanteById (@PathVariable Integer id){
        return solicitantes
                .findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Solicitante não encontrado"));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Solicitante salvar(@RequestBody @Valid Solicitante solicitante){

        Endereco end = enderecos.save(solicitante.getEndereco());
        solicitante.setEndereco(end);
        return solicitantes.save(solicitante);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete( @PathVariable Integer id) {
        solicitantes.findById(id).map(solicitante -> {
                    solicitantes.delete(solicitante);
                    enderecos.delete(solicitante.getEndereco());
                    return solicitante;
                }
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitante não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Solicitante update(@PathVariable Integer id, @RequestBody @Valid Solicitante solicitante){

        solicitantes
                .findById(id)
                .map( solicitanteExistente -> {
                    solicitante.setId(solicitanteExistente.getId());
                    enderecos.save(solicitante.getEndereco());
                    solicitantes.save(solicitante);
                    return solicitanteExistente;
                }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Solicitante não encontrado"));
        return solicitante;
    }

    @GetMapping
    public List<Solicitante> listAll(){
        return solicitantes.findAll();
    }


}
