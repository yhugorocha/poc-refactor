package io.github.yhugorocha.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "cep", length = 15)
    @NotEmpty(message = "Campo cep é obrigatório.")
    private String cep;

    @Column(name = "logradouro", length = 50)
    private String logradouro;

    @Column(name = "bairro", length = 50)
    private String bairro;

    @Column(name = "localidade", length = 50)
    private String localidade;

    @Column(name = "uf", length = 50)
    private String uf;

    @Column(name = "complemento", length = 50)
    private String complemento;

    @Column(name = "numero", length = 10)
    private String numero;

}
