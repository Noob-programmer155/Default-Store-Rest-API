package com.AmrTm.StoreRestAPI.Exception;

import java.util.NoSuchElementException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {
	@ExceptionHandler({ItemNotFoundException.class,NullPointerException.class,UsernameNotFoundException.class,NoSuchElementException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<ExceptionContainer> exception1(Exception ex) {
		return ResponseEntity.ok(new ExceptionContainer(HttpStatus.NOT_FOUND,ex.getMessage()));
	} 
	
	@ExceptionHandler({UsernameAlreadyExistException.class, ItemAlreadyExistException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ExceptionContainer exception2(Exception ex) {
		return new ExceptionContainer(HttpStatus.BAD_REQUEST,ex.getMessage());
	}
	
	@ExceptionHandler({CollisionSubItemException.class})
	@ResponseStatus(HttpStatus.CONFLICT)
	public ExceptionContainer exception3(CollisionSubItemException ex) {
		return new ExceptionContainer(HttpStatus.CONFLICT,ex.getMessage());
	}
	
	@ExceptionHandler({ItemOverloadException.class})
	@ResponseStatus(HttpStatus.INSUFFICIENT_STORAGE)
	public ExceptionContainer exception4(ItemOverloadException ex) {
		return new ExceptionContainer(HttpStatus.INSUFFICIENT_STORAGE,ex.getMessage());
	}
}
