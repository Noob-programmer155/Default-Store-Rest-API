package com.AmrTm.StoreRestAPI.ItemService;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AmrTm.StoreRestAPI.Entity.Item;
import com.AmrTm.StoreRestAPI.ExceptionController.CollisionSubItemException;
import com.AmrTm.StoreRestAPI.ExceptionController.ItemNotFoundException;
import com.AmrTm.StoreRestAPI.LogDataBase.LogData;

@Service
public class ItemConfiguration {
	@Autowired
	private LogData logData;
	
	private static Logger log = LogManager.getLogger(ItemConfiguration.class);
//	private List<Item> items = new LinkedList<Item>();
	private List<SubItems> subItems = new LinkedList<SubItems>();
	
//	public void addItem(Item item) {
//		items.add(item);
//	}
	public void addSubItems(SubItems items) {
		if(subItems.stream().anyMatch(new Predicate<SubItems>() {
			@Override
			public boolean test(SubItems t){
				if(t.getItemType() == items.getItemType() && t.getSubName() == items.getSubName()) {
					return true;
				}
				return false;
			}})) {
			log.error("Adding sub item: "+items.getSubName()+" didn`t succesfully",new CollisionSubItemException("find duplicate sub item with same name and type item"));
		}
		else {
			subItems.add(items);
			logData.saveLog("Adding sub item: "+items.getSubName());
			log.info("Adding sub item: "+items.getSubName()+" succesfully");
		}
	}
	public void addItemSubItems(SubItems subitem, Item item, int mount) {
		try {
		subItems.stream().filter(new Predicate<SubItems>() {
			@Override
			public boolean test(SubItems t) {
				if(t.getItemType() == subitem.getItemType() && t.getSubName() == subitem.getSubName()) {
					return true;
				}
				return false;
			}}).forEach(w -> addingItemsToSubItem(w,item,mount));
			logData.saveLog("Adding item: "+item.getName()+" mount "+mount+" from sub item: "+subitem.getSubName());
			log.info("Adding sub item: "+item.getName()+" mount "+mount+" from sub item: "+subitem.getSubName()+" succesfully");
		}
		catch(NullPointerException e) {
			log.error("Adding sub item: "+item.getName()+" mount "+mount+" from sub item: "+subitem.getSubName()+" didn`t succesfully",new ItemNotFoundException("sub item not found"));
		}
	}
	public void addingItemsToSubItem(SubItems r,Item item, int mount) {
		for(int y = 0;y<mount;y++) 
			r.addItem(item);
	}
//	public void modifyItem(Item item, String name, Long cost) throws ItemNotFoundException {
//		if(items.contains(item)) {
//			items.stream().filter(r -> r.getId() == item.getId()).forEach(new Consumer<Item>() {
//				@Override
//				public void accept(Item t) {
//					t.setCost(cost);
//					t.setName(name);
//				}});
//		}
//		else {
//			throw new ItemNotFoundException("Item not found");
//		}
//	}
	public void modifySubItem(SubItems subItem, String replace, ItemType tipeR) {
		try {
			subItems.stream().filter(new Predicate<SubItems>() {
				@Override
				public boolean test(SubItems t) {
					if(t.getSubName() == subItem.getSubName() && t.getItemType() == subItem.getItemType())
						return true;
					return false;
				}}).forEach(new Consumer<SubItems>() {
					@Override
					public void accept(SubItems t) {
						t.setSubName(replace);
						t.setItemType(tipeR);
					}});
			logData.saveLog("Rename sub item: "+subItem.getSubName()+" to: "+replace);
			log.info("Rename sub item: "+subItem.getSubName()+" to: "+replace+" succesfully");
		}
		catch(NullPointerException u) {
			log.error("Rename sub item: "+subItem.getSubName()+" to: "+replace+" didn`t succesfully", new NullPointerException("Sub item not found"));
		}
	}
	public void modifyItemSubItem(SubItems subItem, Item item) {
		try {
		subItems.stream().filter(new Predicate<SubItems>() {
			@Override
			public boolean test(SubItems t) {
				if(t.getSubName() == subItem.getSubName() && t.getItemType() == subItem.getItemType())
					return true;
				return false;
			}}).forEach(u -> {
			try {
				u.modify(item);
				logData.saveLog("Modify item: "+item.getId()+" from sub item: "+subItem.getSubName());
				log.info("Modify item: "+item.getId()+" from sub item: "+subItem.getSubName()+" succesfully");
			} catch (ItemNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});}
		catch(NullPointerException u) {
			log.error("Modify item: "+item.getId()+" from sub item: "+subItem.getSubName()+" didn`t succesfully",new NullPointerException("Sub item not found"));
		}
	}
//	public void delete(Item item) throws ItemNotFoundException {
//		if(items.contains(item)) {
//			items.remove(item);
//		}
//		else {
//			throw new ItemNotFoundException("Item not found");
//		}
//	}
	public void deleteSubItem(SubItems subItem) throws ItemNotFoundException {
		try {
			if(subItems.contains(subItem)) 
				subItems.remove(subItem);
			logData.saveLog("Remove item: "+subItem.getSubName());
			log.info("Remove sub item: "+subItem.getSubName()+" succesfully");
		}
		catch(NullPointerException u) {
			log.error("Remove sub item: "+subItem.getSubName()+" didn`t succesfully",new NullPointerException("Sub item not found"));
		}
	}
	public void deleteItemSubItem(SubItems subItem, Item item, int mount) {
		try {
			subItems.stream().filter(new Predicate<SubItems>() {
				@Override
				public boolean test(SubItems t) {
					if(t.getItemType()==subItem.getItemType() && t.getSubName() == subItem.getSubName())
						return true;
					return false;
				}}).forEach(y -> {
				try {
					y.delete(item, mount);
					logData.saveLog("Remove item: "+subItem.getSubName()+" mount "+mount+" from sub item: "+subItem.getSubName());
					log.info("Remove item: "+subItem.getSubName()+" mount "+mount+" from sub item: "+subItem.getSubName()+" succesfully");
				} catch (ItemNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		}catch(NullPointerException i) {
			log.error("Remove item: "+subItem.getSubName()+" from sub item: "+subItem.getSubName()+" didn`t succesfully", new NullPointerException("Sub item not found"));
		}
	}
	public List<SubItems> getSubItems() {
		return subItems;
	}
	
	public SubItems getSubItem(String name, ItemType itemType) throws NullPointerException {
		return subItems.stream().filter(new Predicate<SubItems>() {
			@Override
			public boolean test(SubItems t) {
				if(t.getItemType()==itemType && t.getSubName() == name)
					return true;
				return false;
			}}).findFirst().get();
	}
	
	public Item getItemSubItem(SubItems subItem, String id) {
		return subItem.getItem(id);
	}
}
