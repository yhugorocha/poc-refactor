package io.github.yhugorocha.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_gestor_ra")
public class GestorRA {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "nome", length = 50)
    @NotEmpty(message = "Campo nome é obrigatório.")
    private String nome;

    @Column(name = "email", length = 35)
    @NotEmpty(message = "Campo email é obrigatório.")
    private String Email;

    @Column(name = "cpf", length = 15)
    @CPF(message = "Informe um CPF válido")
    private String cpf;

    @Column(name = "telefone", length = 20)
    @NotEmpty(message = "Campo telefone é obrigatório.")
    private String telefone;

    @JoinColumn(name = "id_regiao")
    @ManyToOne
    private Regiao regiao;
}
