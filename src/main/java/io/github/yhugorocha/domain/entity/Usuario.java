package io.github.yhugorocha.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "login", length = 50)
    @NotEmpty(message = "Campo login é obrigatório.")
    private String login;

    @Column(name = "senha")
    @NotEmpty(message = "Campo senha é obrigatório.")
    private String senha;

    @Column(name = "admin")
    private boolean admin;

}
