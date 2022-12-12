package com.te.homedeliveryapplication.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.te.homedeliveryapplication.customexception.CustomException;
import com.te.homedeliveryapplication.responce.Responce;

@RestControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler(CustomException.class)
	public ResponseEntity<Responce> exceptionhandler(CustomException exception) {
		return new ResponseEntity<Responce>(new Responce(true, exception.getMessage(), null), HttpStatus.BAD_REQUEST);
	}
}
