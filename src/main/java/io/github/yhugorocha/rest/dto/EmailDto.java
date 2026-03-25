package io.github.yhugorocha.rest.dto;

import lombok.Data;

import jakarta.validation.constraints.Email;


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
