package com.jfelixy.encurtadorurl.exceptions;


import com.jfelixy.encurtadorurl.model.exceptions.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({FalhaaoPersistirException.class, UrlNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleErrors(Exception ex, HttpServletRequest request){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setPath(request.getRequestURI());
        HttpStatus status;

        if (ex instanceof FalhaaoPersistirException){
            errorResponse.setErrorMessage("Erro ao salvar os dados no banco de dados: " + ex.getMessage());
            errorResponse.setErrorCode("PERSISTENCE_ERROR");
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }else if (ex instanceof UrlNotFoundException){
            errorResponse.setErrorMessage("O recurso solicitado não foi encontrado: " + ex.getMessage());
            errorResponse.setErrorCode("PERSISTENCE_ERROR");
            status = HttpStatus.NOT_FOUND;
        }else{
            errorResponse.setErrorMessage(ex.getMessage());
            errorResponse.setErrorCode("PERSISTENCE_ERROR");
            status = HttpStatus.BAD_REQUEST;
        }

        return new ResponseEntity<>(errorResponse, status);
    }
}
