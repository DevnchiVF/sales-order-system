package com.gestion.pedidos.exception;

public class RecursoYaExisteException extends RuntimeException{

    public RecursoYaExisteException(String campo, String valor){
        super("Ya existe un registro con " + campo + ": " + valor);
    }
}
