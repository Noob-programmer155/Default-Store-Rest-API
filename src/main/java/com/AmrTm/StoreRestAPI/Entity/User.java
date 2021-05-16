package com.AmrTm.StoreRestAPI.Entity;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**Object user
 * @author Amar
 * */

@JsonIgnoreProperties({"username"})
public class User {
	private String username;
	private String codeUser;
	private int countInStore;
	
	public User() {
		super();
	}
	public User(String username) {
		super();
		this.username = username;
		this.codeUser = UUID.randomUUID().toString();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCodeUser() {
		return codeUser;
	}
	public int getCountInStore() {
		return countInStore;
	}
	public void setCountInStore(int countInStore) {
		this.countInStore = countInStore;
	}
}
