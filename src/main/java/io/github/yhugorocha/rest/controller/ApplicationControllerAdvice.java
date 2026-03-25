package io.github.yhugorocha.rest.controller;

import io.github.yhugorocha.exception.RegraNegocioException;
import io.github.yhugorocha.exception.ReservaNaoEncontradaException;
import io.github.yhugorocha.rest.ApiErrors;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handlerRegraNegocioException(RegraNegocioException ex){
        final String mensagemErro = ex.getMessage();
        return new ApiErrors(mensagemErro);
    }

    @ExceptionHandler(ReservaNaoEncontradaException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ApiErrors handlerReservaNotFoundException(ReservaNaoEncontradaException ex){
        return new ApiErrors(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handlerMethodNotValidException(MethodArgumentNotValidException ex){
        final List<String> errors = ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return new ApiErrors(errors);
    }
}
