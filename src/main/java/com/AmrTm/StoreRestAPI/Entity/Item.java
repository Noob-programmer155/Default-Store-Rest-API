package com.AmrTm.StoreRestAPI.Entity;

<<<<<<< HEAD
import java.util.UUID;

public class Item {
	private String id;
	private String name;
	private Long cost;
	public Item(String name, Long cost) {
=======
import java.math.BigDecimal;
import java.util.UUID;

import com.AmrTm.StoreRestAPI.ExceptionController.ItemOverloadException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Object entity for item
 * */
@ApiModel(description="Item entity model")
//@JsonIgnoreProperties({"mount"})
public class Item {
	@ApiModelProperty(notes="property id for item")
	private String id;
	@ApiModelProperty(notes="property name for item")
	private String name;
	@ApiModelProperty(notes="property cost for item")
	private BigDecimal cost;
	@ApiModelProperty(notes="property mount of this item")
	private int mount;
	
	public Item() {
		super();
	}
	
	public Item(String name, BigDecimal cost) {
>>>>>>> a801fe1aca50ff6ebbe8fbba6a77bf8afe0ec7cb
		super();
		this.id = UUID.randomUUID().toString();
		this.name = name;
		this.cost = cost;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
<<<<<<< HEAD
	public Long getCost() {
		return cost;
	}
	public void setCost(Long cost) {
		this.cost = cost;
	}
=======
	public BigDecimal getCost() {
		return cost;
	}
	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	
	public int getMount() {
		return mount;
	}

	public void setMount(int mount) {
		this.mount = mount;
	}
	
>>>>>>> a801fe1aca50ff6ebbe8fbba6a77bf8afe0ec7cb
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
<<<<<<< HEAD
=======

>>>>>>> a801fe1aca50ff6ebbe8fbba6a77bf8afe0ec7cb
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Item))
			return false;
		Item other = (Item) obj;
		if (cost == null) {
			if (other.cost != null)
				return false;
		} else if (!cost.equals(other.cost))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
<<<<<<< HEAD
=======

	public int setUpMount(int mount, int minmount) throws ItemOverloadException {
		if(mount > 0 && mount - minmount > 0)
			return mount - minmount;
		else if(mount - minmount < 0)
			throw new ItemOverloadException("the number of items is not that much");
		else
			return 0;
	}
>>>>>>> a801fe1aca50ff6ebbe8fbba6a77bf8afe0ec7cb
}
