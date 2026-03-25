package io.github.yhugorocha.domain.repositorio;

import io.github.yhugorocha.domain.entity.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface Reservas extends JpaRepository<Reserva, Integer> {

    @Query(value = " SELECT r FROM Reserva r left join fetch r.quadra q where q.id =:idQuadra AND r.status = 'ATIVA' ")
    List<Reserva> reservaPorQuadra(@Param("idQuadra") Integer idQuadra);

    @Query(value = "SELECT r FROM Reserva r " +
            "left join fetch r.solicitante s " +
            "where (s.id =:idSolicitante AND " +
            "r.status = 'ATIVA') ")
    List<Reserva> existeReservaPorSolicitante (@Param("idSolicitante") Integer idSolicitante);

    @Query(value = "SELECT r FROM Reserva r " +
            "left join fetch r.quadra q " +
            "left join fetch r.semana s " +
            "left join fetch r.horario h " +
            "where (q.id =:idQuadra AND " +
            "s.id =:idSemana AND " +
            "h.id =:idHorario AND " +
            "r.status = 'ATIVA')")
    List<Reserva> existeReserva (@Param("idQuadra") Integer idQuadra,
                           @Param("idSemana") Integer idSemana,
                           @Param("idHorario") Integer idHorario);

}
