package io.github.yhugorocha.rest.dto;


import lombok.Data;

import io.github.yhugorocha.domain.entity.Endereco;

@Data
public class QuadraDTO {

    private String nome;
    private Integer qtd_pessoas;
    private Byte[] foto;
    private Integer regiao;
    private Endereco endereco;


}
