package com.AmrTm.StoreRestAPI.ExceptionController;

public class UsernameAlreadyExistException extends Exception {
	public UsernameAlreadyExistException(String message) {
		super(message);
	}
}
