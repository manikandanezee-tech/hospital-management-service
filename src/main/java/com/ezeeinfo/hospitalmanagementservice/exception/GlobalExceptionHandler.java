package com.ezeeinfo.hospitalmanagementservice.exception;

import javax.swing.Renderer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<?>handleInvalidDateFormatException(MethodArgumentTypeMismatchException ex){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid date formate use 'yyyy-mm-dd'");
	}
	@ExceptionHandler(BussinessException.class)
public ResponseEntity<?>bussinessException(BussinessException ex){
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?>resourceNotFoundException(ResourceNotFoundException ex){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

}
