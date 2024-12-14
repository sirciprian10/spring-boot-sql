package com.example.demo.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class ProductExceptionHandler {
    //list of exceptions that will be handled by this method
    @ExceptionHandler(value = {ProductNotFoundException.class})
    public ResponseEntity<Object> handleProductNotFoundException
            (ProductNotFoundException productNotFoundException){
        ProductException productException = new ProductException(
                productNotFoundException.getMessage(),
                    HttpStatus.NOT_FOUND
        );
        return new ResponseEntity<>(productException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {InvalidDataException.class})
    public ResponseEntity<Object> handleProductInvalidOperationException
            (InvalidDataException invalidDataException){
        ProductException productException2 = new ProductException(
                invalidDataException.getMessage(),
                HttpStatus.BAD_REQUEST
        );
        return new ResponseEntity<>(productException2, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        if ("id".equals(ex.getName()) && ex.getRequiredType() == Long.class) {
            // Specific message for ID mismatch
            ProductException productException3 = new ProductException(
                    String.format("Invalid ID value '%s'. It must be a long value.", ex.getValue()),
                    HttpStatus.BAD_REQUEST
            );
            return new ResponseEntity<>(productException3, HttpStatus.BAD_REQUEST);
        }
        // Fallback for other mismatches
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input provided.");
    }

}
