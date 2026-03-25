package io.github.yhugorocha.rest.dto;

import io.github.yhugorocha.domain.entity.Endereco;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InformacoesReservaDTO {

    private Integer codigo;
    private String nomeSolicitante;
    private String cpf;
    private String telefone;
    private String email;
    private String nomeQuadra;
    private Endereco enderecoQuadra;
    private String diaSemana;
    private String horario;
    private String status;

}
