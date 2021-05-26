package com.AmrTm.StoreRestAPI.ItemService;

import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import com.AmrTm.StoreRestAPI.Entity.Item;
import com.AmrTm.StoreRestAPI.ExceptionController.ItemNotFoundException;
import com.AmrTm.StoreRestAPI.ExceptionController.ItemOverloadException;
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
			subItem.stream().filter(r -> r.getId().equals(item.getId())).forEach(t -> {
				t.setCost(item.getCost());
				t.setName(item.getName());
				t.setMount(item.getMount());
			});
		}
		catch(NoSuchElementException | NullPointerException k) {
			throw new ItemNotFoundException("Item not found");
		}
	}
	public void delete(String id, int mount) throws ItemNotFoundException {
		try {
			subItem.stream().filter(r -> r.getId().equals(id)).forEach(e -> {
				try {
					int mt = e.setUpMount(e.getMount(), mount);
					if ( mt != 0)
						e.setMount(mt);
					else
						subItem.remove(e);
					
				} catch (ItemOverloadException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
		}
		catch(NoSuchElementException | NullPointerException y) {
			throw new ItemNotFoundException("Item not found");
		}
		catch(ConcurrentModificationException gf) {
			// some of code
		}
	}
	
	public Item getItem(String id) throws ItemNotFoundException {
		try {
			return subItem.stream().filter(u -> u.getId().equals(id)).findFirst().get();}
		catch(NoSuchElementException | NullPointerException ex) {throw new ItemNotFoundException("Item not found");}
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
