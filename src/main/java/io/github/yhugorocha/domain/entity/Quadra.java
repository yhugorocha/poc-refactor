package io.github.yhugorocha.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "tb_quadra")
public class Quadra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nome")
    @NotEmpty(message = "Campo nome é obrigatório.")
    private String nome;

    @Column(name = "qtd_pessoas")
    private Integer qtd_pessoas;

    @Column(name = "foto")
    private Byte[] foto;

    @JoinColumn(name = "id_regiao")
    @OneToOne
    private Regiao regiao;

    @JoinColumn(name = "id_endereco")
    @OneToOne
    private Endereco endereco;

    @Column(name = "dt_registro")
    private LocalDate dt_registro = LocalDate.now();

    @JsonIgnore
    @OneToMany(mappedBy = "quadra")
    private List<Reserva> reservas;
}
