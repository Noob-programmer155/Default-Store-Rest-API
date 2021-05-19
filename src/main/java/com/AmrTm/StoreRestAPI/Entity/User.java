package com.AmrTm.StoreRestAPI.Entity;

import java.util.LinkedList;
import java.util.List;
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
	private List<Item> purchaseInStore = new LinkedList<>();
	
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
	public void setCodeUser(String codeUser) {
		this.codeUser = codeUser;
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
	public List<Item> getPurchaseInStore() {
		return purchaseInStore;
	}
	public void setPurchaseInStore(Item purchaseInStore) {
		try {
			this.purchaseInStore.add(purchaseInStore);
		}
		catch(OutOfMemoryError e) {
			this.purchaseInStore.remove(0);
			setPurchaseInStore(purchaseInStore);
		}
	}
}
