package io.github.yhugorocha.domain.entity;

import io.github.yhugorocha.domain.enums.StatusEmail;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tb_email")
public class Email {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String owner_ref;

    private String email_from;

    private String email_to;

    private String subject;

    private String texto;

    private LocalDateTime send_date_email;

    private StatusEmail status_email;

}
