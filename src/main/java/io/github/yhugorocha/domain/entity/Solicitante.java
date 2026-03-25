package io.github.yhugorocha.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_solicitante")
public class Solicitante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "cpf")
    @NotEmpty(message = "Campo CPF é obrigatório.")
    @CPF(message = "Informe um CPF válido")
    private String cpf;

    @Column(name = "nome")
    @NotEmpty(message = "Campo nome é obrigatório.")
    private String nome;

    @Column(name = "rg")
    @NotEmpty(message = "Campo rg é obrigatório.")
    private String rg;

    @Column(name = "dtnascimento")
    private LocalDate dtNascimento;

    @Column(name = "telefone")
    @NotEmpty(message = "Campo telefone é obrigatório.")
    private String telefone;

    @Column(name = "email")
    @NotEmpty(message = "Campo email é obrigatório.")
    private String email;

    @JoinColumn(name = "endereco_id")
    @OneToOne
    private Endereco endereco;

    @Column(name = "dtregistro")
    private LocalDate dtRegistro = LocalDate.now();

}
