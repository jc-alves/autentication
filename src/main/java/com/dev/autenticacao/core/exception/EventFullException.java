package com.dev.autenticacao.core.exception;

public class EventFullException extends RuntimeException{

    public EventFullException() {
        super("Erro interno do Servidor!");
    }

    public EventFullException(String message) {
        super(message);
    }
}
