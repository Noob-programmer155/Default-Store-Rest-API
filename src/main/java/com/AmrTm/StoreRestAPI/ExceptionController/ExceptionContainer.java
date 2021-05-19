package com.AmrTm.StoreRestAPI.ExceptionController;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

public class ExceptionContainer {
	private HttpStatus httpStatus;
	@JsonFormat(shape = Shape.STRING, pattern="dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;
	private String debugMessage;
//	private List<ExceptionSubContainer> subErrors;
	public ExceptionContainer(){
		this.timestamp = LocalDateTime.now();
	}
	
	public ExceptionContainer(HttpStatus httpStatus, Throwable ex) {
		super();
		this.httpStatus = httpStatus;
		this.message = "Unexpected Error";
		this.debugMessage = ex.getLocalizedMessage();
	}

	public ExceptionContainer(HttpStatus httpStatus, String message, Throwable ex) {
		super();
		this.httpStatus = httpStatus;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDebugMessage() {
		return debugMessage;
	}

	public void setDebugMessage(String debugMessage) {
		this.debugMessage = debugMessage;
	}

//	public List<ExceptionSubContainer> getSubErrors() {
//		return subErrors;
//	}
//
//	public void setSubErrors(List<ExceptionSubContainer> subErrors) {
//		this.subErrors = subErrors;
//	}
}
