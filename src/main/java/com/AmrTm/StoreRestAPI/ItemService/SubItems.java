package com.AmrTm.StoreRestAPI.ItemService;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import com.AmrTm.StoreRestAPI.Entity.Item;
import com.AmrTm.StoreRestAPI.ExceptionController.ItemNotFoundException;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({"subItem"})
public class SubItems {
	private List<Item> subItem = new LinkedList<Item>();
//	private List<SubItems> subItems = new LinkedList<SubItems>();
	private String subName;
	private ItemType itemType;
	
	public SubItems() {
		super();
	}
	public SubItems(String name, ItemType itemType) {
		super();
		this.subName = name;
		this.itemType = itemType;
	}
	public void addItem(Item item) {
		subItem.add(item);
	}
//	public void addSubItems(SubItems items) {
//		subItems.add(items);
//	}
	public void modify(Item item) throws ItemNotFoundException {
		try {
			subItem.stream().filter(r -> r.getId() == item.getId()).forEach(new Consumer<Item>() {
				@Override
				public void accept(Item t) {
					t.setCost(item.getCost());
					t.setName(item.getName());
				}});
		}
		catch(NullPointerException k) {
			throw new ItemNotFoundException("Item not found");
		}
	}
	public void delete(Item item, int mount) throws ItemNotFoundException {
		try {
			for(int y=0;y<mount;y++) {
				if(subItem.contains(item)) {
					subItem.remove(item);
		}}}
		catch(NullPointerException y) {
			throw new ItemNotFoundException("Item not found");
		}
	}
	
	public Item getItem(String id) {
		return subItem.stream().filter(u -> u.getId() == id).findFirst().get();
	}
	
	public String getSubName() {
		return subName;
	}
	public void setSubName(String subName) {
		this.subName = subName;
	}
	public List<Item> getSubItem() {
		return subItem;
	}
	public void setSubItem(List<Item> subItem) {
		this.subItem = subItem;
	}
	//	public List<SubItems> getSubItems() {
//		return subItems;
//	}
//	public void setSubItems(List<SubItems> subItems) {
//		this.subItems = subItems;
//	}
	public ItemType getItemType() {
		return itemType;
	}
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}
}
