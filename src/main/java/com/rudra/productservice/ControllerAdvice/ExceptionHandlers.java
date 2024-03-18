package com.rudra.productservice.ControllerAdvice;

import com.rudra.productservice.Exceptions.ProductNotExistsException;
import com.rudra.productservice.dtos.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandlers {

    public ResponseEntity<ExceptionDto> handleProductNotExistException (ProductNotExistsException exception){
        ExceptionDto dto = new ExceptionDto();
        dto.setMessage(exception.getMessage());

        return new ResponseEntity<>(dto, HttpStatus.OK);

    }
}
