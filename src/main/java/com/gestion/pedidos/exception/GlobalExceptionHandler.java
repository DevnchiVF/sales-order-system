package com.gestion.pedidos.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.Map;
import java.util.stream.Collectors;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ProblemDetail handleNoEncontrado(RecursoNoEncontradoException ex) {
        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setTitle("Recurso no encontrado");
        problem.setType(URI.create("https://api.gestion.com/errores/no-encontrado"));
        return problem;
    }

    @ExceptionHandler(RecursoYaExisteException.class)
    public ProblemDetail handleYaExiste(RecursoYaExisteException ex) {
        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problem.setTitle("Conflicto de datos");
        problem.setType(URI.create("https://api.gestion.com/errores/conflicto"));
        return problem;
    }

    @ExceptionHandler(StockInsuficienteException.class)
    public ProblemDetail handleStockInsuficiente(StockInsuficienteException ex){
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problem.setTitle("Stock insuficiente");
        problem.setType(URI.create("https://api.gestion.com/errores/stock-insuficiente"));
        return problem;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleValidacion(MethodArgumentNotValidException ex) {
        Map<String, String> errores = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage
                ));

        ProblemDetail problem = ProblemDetail
                .forStatusAndDetail(HttpStatus.BAD_REQUEST, "Hay campos inválidos en la petición");
        problem.setTitle("Error de validación");
        problem.setType(URI.create("https://api.gestion.com/errores/validacion"));
        problem.setProperty("errores", errores);
        return problem;
    }

    @ExceptionHandler(Exception.class)
    public ProblemDetail handleException(Exception ex){
        log.error("Error no controlado: {}", ex.getMessage(), ex);
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrió un error interno. Intente más tarde.");
        problem.setTitle("Error interno del servidor");
        return  problem;
    }
}
