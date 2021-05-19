package com.AmrTm.StoreRestAPI.ExceptionController;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExceptionController {
	@ExceptionHandler(ItemNotFoundException.class)
	public ExceptionContainer exception1(Exception ex) {
		return new ExceptionContainer(HttpStatus.NOT_FOUND,ex.getMessage(), ex.getCause());
	} 
	
	// because this REST is setup error handling at each service and rest, this exception controller no use 
}
