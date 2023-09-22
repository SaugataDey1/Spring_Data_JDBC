package com.lcwd.todo.todomanager.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler
{
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // We have to create Handler methods for specific Exception
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<String> NullPointerExceptionHandler(NullPointerException ex)
    {
        logger.info("Its NullPointer Exception from Global Handler");
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Handling Resource not Found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handlerResourceNotFoundException(ResourceNotFoundException ex)
    {
          logger.info("ERROR : {}", ex.getMessage());

          ExceptionResponse response = new ExceptionResponse();
          response.setMessage(ex.getMessage());
          response.setStatus(HttpStatus.NOT_FOUND);
          response.setSuccess(false);

          return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
}
