package io.github.yhugorocha.rest.controller;

import io.github.yhugorocha.domain.entity.Reserva;
import io.github.yhugorocha.domain.enums.StatusReserva;
import io.github.yhugorocha.rest.dto.AtualizaStatusReservaDTO;
import io.github.yhugorocha.rest.dto.InformacoesReservaDTO;
import io.github.yhugorocha.rest.dto.ReservaDTO;
import io.github.yhugorocha.service.ReservaService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/agenda/reserva/")
public class ReservaController {

    private ReservaService service;

    public ReservaController(ReservaService service){
        this.service = service;
    }

    @GetMapping()
    public List<Reserva> findall() {
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Reserva save(@RequestBody ReservaDTO reservaDto){
        return service.save(reservaDto);
    }

    @GetMapping("{id}")
    public InformacoesReservaDTO getById(@PathVariable Integer id){
        return service.obterDadosReserva(id);
    }

    @GetMapping("quadra/{id}")
    public List<Reserva> getPorQuadra(@PathVariable Integer id){
        return service.obterReservasPorQuadra(id);
    }

    @PatchMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable Integer id, @RequestBody AtualizaStatusReservaDTO dto){
        final String status = dto.getNovoStatus();
        service.atualizaStatus(id, StatusReserva.valueOf(status));
    }
}
