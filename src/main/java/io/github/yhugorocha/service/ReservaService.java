package io.github.yhugorocha.service;

import io.github.yhugorocha.domain.entity.Reserva;
import io.github.yhugorocha.domain.enums.StatusReserva;
import io.github.yhugorocha.rest.dto.InformacoesReservaDTO;
import io.github.yhugorocha.rest.dto.ReservaDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ReservaService {
    Reserva save(ReservaDTO reservaDto);

    List<Reserva> obterReservasPorQuadra(Integer id);

    InformacoesReservaDTO obterDadosReserva(Integer id);

    void atualizaStatus(Integer id, StatusReserva statusReserva);
}
