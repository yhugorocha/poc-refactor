package io.github.yhugorocha.exception;

public class ReservaNaoEncontradaException extends RuntimeException {
    public ReservaNaoEncontradaException(){
        super("Reserva não encontrada");
    }
}
