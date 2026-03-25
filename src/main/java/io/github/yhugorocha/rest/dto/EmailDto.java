package io.github.yhugorocha.rest.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
public class EmailDto {


    private String owner_ref;


    @Email
    private String email_from;


    @Email
    private String email_to;


    private String subject;


    private String texto;


}
