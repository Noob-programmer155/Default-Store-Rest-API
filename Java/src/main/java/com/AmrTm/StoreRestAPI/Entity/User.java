package com.AmrTm.StoreRestAPI.Entity;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**Object entity for user
 * @author Amar
 * */

//@JsonIgnoreProperties({"username","purchaseInStore"})
@ApiModel(description="User entity model")
public class User {
	@ApiModelProperty(notes="this property mount username for user")
	private String username;
	@ApiModelProperty(notes="this property mount secret code for user")
	private String codeUser;
	@ApiModelProperty(notes="the number of times a customer has entered the store")
	private int countInStore;
	@ApiModelProperty(notes="items purchased by customers (used for sales data analysis needs)")
	private List<Item> purchaseInStore = new LinkedList<>();
	
	public User() {
		super();
	}
	public User(String username) {
		super();
		this.username = username;
		this.codeUser = UUID.randomUUID().toString();
		this.countInStore = 0;
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
	public void setItemPurchaseInStore(Item purchaseInStore) {
		try {
			this.purchaseInStore.add(purchaseInStore);
		}
		catch(OutOfMemoryError e) {
			this.purchaseInStore.remove(0);
			setItemPurchaseInStore(purchaseInStore);
		}
	}
	public void setPurchaseInStore(List<Item> purchaseInStore) {
		this.purchaseInStore = purchaseInStore;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeUser == null) ? 0 : codeUser.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof User))
			return false;
		User other = (User) obj;
		if (codeUser == null) {
			if (other.codeUser != null)
				return false;
		} else if (!codeUser.equals(other.codeUser))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
}
