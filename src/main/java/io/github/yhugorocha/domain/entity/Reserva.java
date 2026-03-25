package io.github.yhugorocha.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import io.github.yhugorocha.domain.enums.StatusReserva;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JoinColumn(name = "id_solicitante")
    @OneToOne
    private Solicitante solicitante;

    @JoinColumn(name = "id_quadra")
    @ManyToOne
    private Quadra quadra;

    @Column(name = "dt_inicio")
    private LocalDate dtInicio;

    @Column(name = "dt_final")
    private LocalDate dtFinal;

    @Column(name = "dt_registro")
    private LocalDate dtRegistro = LocalDate.now();

    @JoinColumn(name = "id_semana")
    @OneToOne
    private Semana semana;

    @JoinColumn(name = "id_horario")
    @OneToOne
    private Horario horario;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusReserva status;

}
