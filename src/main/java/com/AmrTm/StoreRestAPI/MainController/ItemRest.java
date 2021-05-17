package com.AmrTm.StoreRestAPI.MainController;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.AmrTm.StoreRestAPI.Entity.Item;
import com.AmrTm.StoreRestAPI.ItemService.ItemConfiguration;
import com.AmrTm.StoreRestAPI.ItemService.ItemType;
import com.AmrTm.StoreRestAPI.ItemService.SubItems;

@RestController
@RequestMapping("/items")
public class ItemRest {
	@Autowired
	private ItemConfiguration itemConfig;
	
	@GetMapping("/subitem")
	public List<SubItems> getSubItem(){
		return itemConfig.getSubItems();
	}
	
	@GetMapping("subitem/{name}/items")
	public List<Item> getItems(@PathVariable String name, @RequestAttribute String type){
		return itemConfig.getSubItem(name, ItemType.valueOf(type)).getSubItem();
	}
	
	@GetMapping("subitem/{name}/items/{id}")
	public EntityModel<Item> getItem(@PathVariable String name, @RequestAttribute String type, @PathVariable String id){
		EntityModel<Item> item = EntityModel.of(itemConfig.getItemSubItem(itemConfig.getSubItem(name, ItemType.valueOf(type)), id));
		WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ItemRest.class).getItems(name, type));
		item.add(link.withRel("all-items-in-subitem"));
		return item;
	}
	
	@GetMapping("/subitem/{name}")
	public EntityModel<SubItems> getSubItem(@PathVariable String name, @RequestAttribute String type){
		EntityModel<SubItems> subitem = EntityModel.of(itemConfig.getSubItem(name, ItemType.valueOf(type)));
		WebMvcLinkBuilder sub = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ItemRest.class).getSubItem());
		WebMvcLinkBuilder item = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ItemRest.class).getItems(name, type));
		subitem.add(item.withRel("all-items-in-subitem"),sub.withRel("all-subitem"));
		return subitem;
	}
	
	@PostMapping("/save/subitem")
	public ResponseEntity<SubItems> addSubItems(@RequestBody SubItems subItem){
		SubItems sub = new SubItems(subItem.getSubName(),subItem.getItemType());
		itemConfig.addSubItems(sub);
		return ResponseEntity.ok(sub);
	}
	
	
}
