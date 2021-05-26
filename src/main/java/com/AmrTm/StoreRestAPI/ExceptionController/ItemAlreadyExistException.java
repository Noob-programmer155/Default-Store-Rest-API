package com.AmrTm.StoreRestAPI.ExceptionController;

public class ItemAlreadyExistException extends Exception{

	public ItemAlreadyExistException(String message) {
		super(message);
	}
	
}
