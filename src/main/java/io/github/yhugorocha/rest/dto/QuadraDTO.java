package io.github.yhugorocha.rest.dto;


import io.github.yhugorocha.domain.entity.Endereco;
import lombok.Data;

@Data
public class QuadraDTO {

    private String nome;
    private Integer qtd_pessoas;
    private Byte[] foto;
    private Integer regiao;
    private Endereco endereco;


}
