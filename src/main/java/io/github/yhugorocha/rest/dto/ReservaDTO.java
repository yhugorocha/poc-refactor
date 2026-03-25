package io.github.yhugorocha.rest.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservaDTO {

    private Integer solicitante;
    private Integer quadra;
    private LocalDate dt_inicio;
    private LocalDate dt_final;
    private LocalDate dt_registro;
    private Integer semana;
    private Integer horario;
}
