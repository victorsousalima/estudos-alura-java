package br.com.alura.adopet.api.exception;

public class DadosNotFound extends RuntimeException {

    public DadosNotFound(String msg) {
        super(msg);
    }
}
