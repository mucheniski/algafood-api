package com.algaworks.algafood.exception;

public class ArmazenamentoException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ArmazenamentoException(String message) {
        super(message);
    }

    public ArmazenamentoException(String message, Throwable cause) {
        super(message, cause);
    }
}
