package com.AmrTm.StoreRestAPI.Entity;

<<<<<<< HEAD
=======
import java.util.LinkedList;
import java.util.List;
>>>>>>> a801fe1aca50ff6ebbe8fbba6a77bf8afe0ec7cb
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

<<<<<<< HEAD
/**Object user
 * @author Amar
 * */

@JsonIgnoreProperties({"username"})
public class User {
	private String username;
	private String codeUser;
	private int countInStore;
=======
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
>>>>>>> a801fe1aca50ff6ebbe8fbba6a77bf8afe0ec7cb
	
	public User() {
		super();
	}
	public User(String username) {
		super();
<<<<<<< HEAD
		this.username = username;
		this.codeUser = UUID.randomUUID().toString();
=======
		this.codeUser = UUID.randomUUID().toString();
		this.username = username;
		this.countInStore = 0;
>>>>>>> a801fe1aca50ff6ebbe8fbba6a77bf8afe0ec7cb
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
<<<<<<< HEAD
=======
	public void setCodeUser(String codeUser) {
		this.codeUser = codeUser;
	}
>>>>>>> a801fe1aca50ff6ebbe8fbba6a77bf8afe0ec7cb
	public String getCodeUser() {
		return codeUser;
	}
	public int getCountInStore() {
		return countInStore;
	}
	public void setCountInStore(int countInStore) {
		this.countInStore = countInStore;
	}
<<<<<<< HEAD
=======
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
>>>>>>> a801fe1aca50ff6ebbe8fbba6a77bf8afe0ec7cb
}
