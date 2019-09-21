package com.evacodekitchen.realestateportalserver.rest;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.evacodekitchen.realestateportalserver.rest.dto.ErrorDTO;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
	
	@ExceptionHandler(NoSuchElementException.class)
	ResponseEntity<ErrorDTO> handleNoSuchElementException(NoSuchElementException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(ex.getMessage(), HttpStatus.NOT_FOUND.value()));
	}
}
