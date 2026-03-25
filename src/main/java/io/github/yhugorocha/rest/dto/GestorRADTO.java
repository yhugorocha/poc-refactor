package io.github.yhugorocha.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GestorRADTO {

    private String nome;
    private String email;
    private String cpf;
    private String telefone;
    private Integer regiao;

}
