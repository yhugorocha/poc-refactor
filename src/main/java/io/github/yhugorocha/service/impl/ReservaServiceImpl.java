package io.github.yhugorocha.service.impl;

import io.github.yhugorocha.domain.entity.*;
import io.github.yhugorocha.domain.enums.StatusReserva;
import io.github.yhugorocha.domain.repositorio.*;
import io.github.yhugorocha.exception.RegraNegocioException;
import io.github.yhugorocha.exception.ReservaNaoEncontradaException;
import io.github.yhugorocha.rest.dto.InformacoesReservaDTO;
import io.github.yhugorocha.rest.dto.ReservaDTO;
import io.github.yhugorocha.service.ReservaService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReservaServiceImpl implements ReservaService {

    @Autowired
    Reservas reservas;
    @Autowired
    Quadras quadras;
    @Autowired
    Solicitantes solicitantes;
    @Autowired
    Semanas semanas;
    @Autowired
    Horarios horarios;


    @Override
    @Transactional
    public Reserva save(ReservaDTO reservaDto) {

        final Reserva reserva = new Reserva();

        final Solicitante solicitante = solicitantes.findById(reservaDto.getSolicitante())
                .orElseThrow(() -> new RegraNegocioException("Solicitante não encontrado"));

        final Quadra quadra = quadras.findById(reservaDto.getQuadra())
                .orElseThrow(() -> new RegraNegocioException("Quadra não encontrada"));

        final Semana semana = semanas.findById(reservaDto.getSemana())
                .orElseThrow(() -> new RegraNegocioException("Dia da semana não encontrada"));

        final Horario horario = horarios.findById(reservaDto.getHorario())
                .orElseThrow(() -> new RegraNegocioException("Horario não encontrada"));

        reserva.setSolicitante(solicitante);
        reserva.setQuadra(quadra);
        reserva.setDtInicio(reservaDto.getDt_inicio());
        reserva.setDtFinal(reservaDto.getDt_final());
        reserva.setDtRegistro(LocalDate.now());
        reserva.setSemana(semana);
        reserva.setHorario(horario);
        reserva.setStatus(StatusReserva.ATIVA);

        final Boolean s = existeReservaPorSolicitante(reserva);

        if(s){
            final Boolean r = existeReserva(reserva);
            if(r){
                return reservas.save(reserva);
            }else{
                throw new RegraNegocioException("Já existe uma reserva cadastrada");
            }
        }else{
            throw new RegraNegocioException("Já existe uma reserva cadastrada para o solicitante");
        }

    }

    public List<Reserva> obterReservasPorQuadra(Integer idQuadra){

        return this.reservas.reservaPorQuadra(idQuadra);

    }

    @Override
    public InformacoesReservaDTO obterDadosReserva(Integer id) {

        return reservas.findById(id)
                .map(this::converter)
                .orElseThrow(() -> new RegraNegocioException("Reserva não encontrada"));
    }

    @Override
    @Transactional
    public void atualizaStatus(Integer id, StatusReserva statusReserva) {
        reservas.findById(id).map(reserva -> {
            reserva.setStatus(statusReserva);
            return reservas.save(reserva);
        }).orElseThrow(ReservaNaoEncontradaException::new);
    }

    public List<Reserva> findAll(){
        return reservas.findAll();
    }

    private InformacoesReservaDTO converter(Reserva reserva){
        return InformacoesReservaDTO.builder()
                .codigo(reserva.getId())
                .nomeSolicitante(reserva.getSolicitante().getNome())
                .cpf(reserva.getSolicitante().getCpf())
                .email(reserva.getSolicitante().getEmail())
                .telefone(reserva.getSolicitante().getTelefone())
                .nomeQuadra(reserva.getQuadra().getNome())
                .enderecoQuadra(reserva.getQuadra().getEndereco())
                .diaSemana(reserva.getSemana().getDia())
                .status(reserva.getStatus().name())
                .horario(reserva.getHorario().getHora()).build();
    }

    private Boolean existeReserva(Reserva reserva){
        final Integer idQuadra = reserva.getQuadra().getId();
        final Integer idSemana = reserva.getSemana().getId();
        final Integer idHorario = reserva.getHorario().getId();

        final List<Reserva> r = reservas.existeReserva(idQuadra, idSemana, idHorario);
        return r.isEmpty();
    }

    private Boolean existeReservaPorSolicitante(Reserva reserva){
        final Integer idSolicitante = reserva.getSolicitante().getId();

        final List<Reserva> s = reservas.existeReservaPorSolicitante(idSolicitante);
        return s.isEmpty();
    }



}
