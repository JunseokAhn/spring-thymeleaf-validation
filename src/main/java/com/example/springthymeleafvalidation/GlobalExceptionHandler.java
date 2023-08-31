package com.example.springthymeleafvalidation;

import com.example.springthymeleafvalidation.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {

    // Exception 발생시, 오류메시지와 오류코드 반환
    @ExceptionHandler(Exception.class)
    public ResponseEntity catchException(Exception e) {
        return new ResponseEntity(new ResponseDTO(EnumStatus.BAD_REQUEST, e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity catchNoSuchException(Exception e) {
        return new ResponseEntity(new ResponseDTO(EnumStatus.RESOURCE_NOT_FOUND, e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity catchNullPointerException(Exception e) {
        return new ResponseEntity(new ResponseDTO(EnumStatus.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    public ResponseEntity catchIndexOutOfBoundsException(Exception e) {
        return new ResponseEntity(new ResponseDTO(EnumStatus.INTERNAL_SERVER_ERROR, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

