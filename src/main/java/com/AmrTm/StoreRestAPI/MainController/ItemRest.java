package com.AmrTm.StoreRestAPI.MainController;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.AmrTm.StoreRestAPI.Entity.Item;
import com.AmrTm.StoreRestAPI.ExceptionController.ItemNotFoundException;
import com.AmrTm.StoreRestAPI.ItemService.ItemConfiguration;
import com.AmrTm.StoreRestAPI.ItemService.ItemType;
import com.AmrTm.StoreRestAPI.ItemService.SubItems;

@RestController
@RequestMapping("/items")
public class ItemRest {
	@Autowired
	private ItemConfiguration itemConfiguration;
	
	@GetMapping("/subitems")
	public List<SubItems> getSubItem(){
		return itemConfiguration.getSubItems();
	}
	
	@GetMapping("/subitem/{name}/items")
	public List<Item> getItems(@PathVariable String name, @RequestAttribute String type){
		return itemConfiguration.getSubItem(name, ItemType.valueOf(type)).getSubItem();
	}
	
	@GetMapping("/subitem/{name}/item/{id}")
	public EntityModel<Item> getItem(@PathVariable String name, @RequestAttribute String type, @PathVariable String id){
		EntityModel<Item> item = EntityModel.of(itemConfiguration.getItemSubItem(itemConfiguration.getSubItem(name, ItemType.valueOf(type)), id));
		WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ItemRest.class).getItems(name, type));
		item.add(link.withRel("all-items-in-subitem"));
		return item;
	}
	
	@GetMapping("/subitem/{name}")
	public EntityModel<SubItems> getSubItem(@PathVariable String name, @RequestAttribute String type){
		EntityModel<SubItems> subitem = EntityModel.of(itemConfiguration.getSubItem(name, ItemType.valueOf(type)));
		WebMvcLinkBuilder sub = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ItemRest.class).getSubItem());
		WebMvcLinkBuilder item = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ItemRest.class).getItems(name, type));
		subitem.add(item.withRel("all-items-in-subitem"),sub.withRel("all-subitem"));
		return subitem;
	}
	
	@PostMapping("/save/subitem")
	public ResponseEntity<SubItems> addSubItem(@RequestBody SubItems subItem){
		SubItems sub = new SubItems(subItem.getSubName(),subItem.getItemType());
		itemConfiguration.addSubItems(sub);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}").buildAndExpand(sub.getSubName()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping("/save/subitem/{name}/item")
	public ResponseEntity<Item> addItemSubItem(@RequestBody Item item, @PathVariable String name, @RequestAttribute String type, @RequestAttribute int mount){
		SubItems sub = itemConfiguration.getSubItem(name, ItemType.valueOf(type));
		itemConfiguration.addItemSubItems(sub, item, mount);
		return ResponseEntity.ok(item);
	}
	
	@PutMapping("/modify/subitem/{name}")
	public ResponseEntity<SubItems> modifySubItem(@RequestBody SubItems sub,@PathVariable String name, @RequestAttribute String type){
		SubItems subitem = itemConfiguration.getSubItem(name, ItemType.valueOf(type));
		itemConfiguration.modifySubItem(subitem, sub.getSubName(), sub.getItemType());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}").buildAndExpand(sub.getSubName()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/modify/subitem/{name}/item")
	public ResponseEntity<Item> modifyItemSubItem(@RequestBody Item item,@PathVariable String name ,@RequestAttribute String type){
		SubItems subItem = itemConfiguration.getSubItem(name, ItemType.valueOf(type));
		itemConfiguration.modifyItemSubItem(subItem, item);
		return ResponseEntity.ok(item);
	}
	
	@DeleteMapping("/delete/subitem/{name}")
	public String deleteSubItem(@PathVariable String name, @RequestAttribute String type) throws NullPointerException, ItemNotFoundException {
		itemConfiguration.deleteSubItem(itemConfiguration.getSubItem(name, ItemType.valueOf(type)));
		return "sub item "+name+" with sub type "+type+" has been deleted";
	}
	
	@DeleteMapping("/delete/subitem/{name}/item/{id}")
	public String deleteItemSubItem(@PathVariable String name, @PathVariable String id,@RequestAttribute String type, @RequestAttribute int mount) {
		SubItems sub = itemConfiguration.getSubItem(name, ItemType.valueOf(type));
		itemConfiguration.deleteItemSubItem(sub, itemConfiguration.getItemSubItem(sub, id), mount);
		return "item with id ["+id+"] was succesfully deleted" ;
	}
}
