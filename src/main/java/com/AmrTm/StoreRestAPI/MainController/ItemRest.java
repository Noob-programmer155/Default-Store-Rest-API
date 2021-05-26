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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.AmrTm.StoreRestAPI.Entity.Item;
import com.AmrTm.StoreRestAPI.ExceptionController.CollisionSubItemException;
import com.AmrTm.StoreRestAPI.ExceptionController.ItemNotFoundException;
import com.AmrTm.StoreRestAPI.ItemService.ItemConfiguration;
import com.AmrTm.StoreRestAPI.ItemService.ItemType;
import com.AmrTm.StoreRestAPI.ItemService.SubItems;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/items")
@Api(tags="item")
public class ItemRest {
	@Autowired
	private ItemConfiguration itemConfiguration;
	
	@GetMapping("/subitems")
	@ApiOperation(value="Api for get all sub items",response=List.class)
	public List<SubItems> getSubItem(){
		return itemConfiguration.getSubItems();
	}
	
	@GetMapping("/subitem/{name}/items")
	@ApiOperation(value = "Api for get all item in specified sub item", response=List.class)
	@ApiResponses({
		@ApiResponse(code=404, message="sub item not found")
	})
	public List<Item> getItems(@ApiParam("name of sub item") @PathVariable String name, 
							   @ApiParam("type of sub item") @RequestParam String type) {
		return itemConfiguration.getSubItem(name, ItemType.valueOf(type)).getSubItem();
	}
		
	@GetMapping("/subitem/{name}/item")
	@ApiOperation(value="Api for get item in sub item", response=EntityModel.class)
	@ApiResponses({
		@ApiResponse(code=404, message="sub item not found"),
		@ApiResponse(code=404, message="Item not found")
	})
	public EntityModel<Item> getItem(@ApiParam("name of sub item") @PathVariable String name, 
									 @ApiParam("type of sub item") @RequestParam String type, 
									 @ApiParam("id item") @RequestParam String id) throws NullPointerException, ItemNotFoundException{
		EntityModel<Item> item = EntityModel.of(itemConfiguration.getItemSubItem(itemConfiguration.getSubItem(name, ItemType.valueOf(type)), id));
		WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ItemRest.class).getItems(name, type));
		item.add(link.withRel("all-items-in-subitem"));
		return item;
	}
	
	@GetMapping("/subitem/{name}")
	@ApiOperation(value="Api for get sub item", response=EntityModel.class)
	@ApiResponses({
		@ApiResponse(code=404, message="sub item not found")
	})
	public EntityModel<SubItems> getSubItem(@ApiParam("name of sub item") @PathVariable String name, 
											@ApiParam("type of sub item") @RequestParam String type){
		EntityModel<SubItems> subitem = EntityModel.of(itemConfiguration.getSubItem(name, ItemType.valueOf(type)));
		WebMvcLinkBuilder sub = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ItemRest.class).getSubItem());
		WebMvcLinkBuilder item = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ItemRest.class).getItems(name, type));
		subitem.add(item.withRel("all-items-in-subitem"),sub.withRel("all-subitem"));
		return subitem;
	}
	
	@PostMapping("/save/subitem")
	@ApiOperation(value="Api for add new sub item", response=ResponseEntity.class)
	@ApiResponses({
		@ApiResponse(code=409, message="find duplicate sub item with same name and type item")
	})
	public ResponseEntity<SubItems> addSubItem(@ApiParam("new subitem") @RequestBody SubItems subItem) throws CollisionSubItemException{
		SubItems sub = new SubItems(subItem.getSubName(),subItem.getItemType());
		itemConfiguration.addSubItems(sub);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}").buildAndExpand(sub.getSubName()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PostMapping("/save/subitem/{name}/item")
	@ApiOperation(value="Api for add item in sub item", response=ResponseEntity.class)
	@ApiResponses({
		@ApiResponse(code=404, message="sub item not found"),
		@ApiResponse(code=400, message="Item already exist in this sub item")
	})
	public ResponseEntity<Item> addItemSubItem(@ApiParam("new item for sub item") @RequestBody Item item, 
											   @ApiParam("name of sub item") @PathVariable String name, 
											   @ApiParam("type of sub item") @RequestParam String type, 
											   @ApiParam("mount of item which is added") @RequestParam int mount) throws ItemNotFoundException{
		SubItems sub = itemConfiguration.getSubItem(name, ItemType.valueOf(type));
		Item it = null;
		if(item.getId() != null)
			it = item;
		else
			it = new Item(item.getName(),item.getCost());
		itemConfiguration.addItemSubItems(sub, it, mount);
		return ResponseEntity.ok(it);
	}
	
	@PutMapping("/modify/subitem/{name}")
	@ApiOperation(value="Api for modify sub item", response=ResponseEntity.class)
	@ApiResponses({
		@ApiResponse(code=404, message="sub item not found")
	})
	public ResponseEntity<SubItems> modifySubItem(@ApiParam("modified sub item") @RequestBody SubItems sub,
												  @ApiParam("name of sub item") @PathVariable String name, 
												  @ApiParam("type of sub item") @RequestParam String type) throws ItemNotFoundException{
		SubItems subitem = itemConfiguration.getSubItem(name, ItemType.valueOf(type));
		itemConfiguration.modifySubItem(subitem, sub.getSubName(), sub.getItemType());
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{name}").buildAndExpand(sub.getSubName()).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@PutMapping("/modify/subitem/{name}/item")
	@ApiOperation(value="Api for modify sub item", response=ResponseEntity.class)
	@ApiResponses({
		@ApiResponse(code=404, message="sub item not found"),
		@ApiResponse(code=404, message="Item not found")
	})
	public ResponseEntity<Item> modifyItemSubItem(@ApiParam("modified Item") @RequestBody Item item,
												  @ApiParam("name of sub item") @PathVariable String name ,
												  @ApiParam("type of sub item") @RequestParam String type) throws ItemNotFoundException{
		SubItems subItem = itemConfiguration.getSubItem(name, ItemType.valueOf(type));
		itemConfiguration.modifyItemSubItem(subItem, item);
		return ResponseEntity.ok(item);
	}
	
	@DeleteMapping("/delete/subitem/{name}")
	@ApiOperation(value="Api for delete sub item", response=String.class)
	@ApiResponses({
		@ApiResponse(code=404, message="sub item not found")
	})
	public String deleteSubItem(@ApiParam("name of sub item") @PathVariable String name, 
			 					@ApiParam("type of sub item") @RequestParam String type) throws NullPointerException, ItemNotFoundException {
		itemConfiguration.deleteSubItem(itemConfiguration.getSubItem(name, ItemType.valueOf(type)));
		return "sub item "+name+" with sub type "+type+" has been deleted";
	}
	
	@DeleteMapping("/delete/subitem/{name}/item")
	@ApiOperation(value="Api for delete item", response=String.class)
	@ApiResponses({
		@ApiResponse(code=404, message="sub item not found"),
		@ApiResponse(code=404, message="Item not found")
	})
	public String deleteItemSubItem(@ApiParam("name of sub item") @PathVariable String name, 
									@ApiParam("item id") @RequestParam String id,
									@ApiParam("type of sub item") @RequestParam String type, 
									@ApiParam("mount of item to be deleted") @RequestParam int mount) throws ItemNotFoundException {
		SubItems sub = itemConfiguration.getSubItem(name, ItemType.valueOf(type));
		itemConfiguration.deleteItemSubItem(sub, itemConfiguration.getItemSubItem(sub, id), mount);
		return "item with id ["+id+"] was succesfully deleted" ;
	}
}
