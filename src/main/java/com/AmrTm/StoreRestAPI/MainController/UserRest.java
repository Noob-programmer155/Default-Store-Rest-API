package com.AmrTm.StoreRestAPI.MainController;

import java.net.URI;
import java.util.NavigableSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.AmrTm.StoreRestAPI.Entity.Item;
import com.AmrTm.StoreRestAPI.Entity.User;
import com.AmrTm.StoreRestAPI.ExceptionController.UsernameAlreadyExistException;
import com.AmrTm.StoreRestAPI.UserService.UserConfiguration;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/users")
@Api(tags="user")
public class UserRest {
	
	@Autowired
	private UserConfiguration userConfiguration;
	
	@GetMapping("/all")
	@ApiOperation(value="Api for get all data user", response=NavigableSet.class)
	public NavigableSet<User> getUsers(){
		return userConfiguration.getUsers();
	}
	
	@GetMapping("/user")
	@ApiOperation(value="Api for get data user with specific id user", response=EntityModel.class)
	@ApiResponses({
		@ApiResponse(code=404, message="User not found")
	})
	public EntityModel<User> getUser(@ApiParam("id user") @RequestParam String id){
		EntityModel<User> model = EntityModel.of(userConfiguration.getUserByIdCode(id));
		WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserRest.class).getUsers());
		model.add(link.withRel("all-users"));
		return model;
	}
	
	@PostMapping("/save")
	@ApiOperation(value="Api for add new user", response=ResponseEntity.class)
	@ApiResponses({
		@ApiResponse(code=400, message="User already exist")
	})
	public ResponseEntity<User> addUser(@ApiParam("new User") @RequestBody User user) throws UsernameAlreadyExistException{
		User gf = new User(user.getUsername());
		userConfiguration.save(gf);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(gf.getCodeUser()).toUri();
		return ResponseEntity.created(uri).build();
	} 
	
	@PutMapping("/modify/{id}")
	@ApiOperation(value="Api for modify specified user of the many arrivals", response=ResponseEntity.class)
	@ApiResponses({
		@ApiResponse(code=404,message="User not found")
	})
	public ResponseEntity<User> modifyUser(@ApiParam("id user") @PathVariable String id){
		User user = userConfiguration.getUserByIdCode(id);
		userConfiguration.modifyCountIn(user);
		return ResponseEntity.ok(user);
	}
	
	@PutMapping("/modify/{id}/item")
	@ApiOperation(value="Api for modify specified user from item purchase", response=ResponseEntity.class)
	@ApiResponses({
		@ApiResponse(code=404,message="User not found")
	})
	public ResponseEntity<User> modifyUser(@ApiParam("User item purchased") @RequestBody Item item, 
										   @ApiParam("id user") @PathVariable String id){
		User user = userConfiguration.getUserByIdCode(id);
		userConfiguration.modifyCountIn(user,item);
		return ResponseEntity.ok(user);
	}
	
	@DeleteMapping("/delete/{id}")
	@ApiOperation(value="Api for delete specified user", response=ResponseEntity.class)
	@ApiResponses({
		@ApiResponse(code=404, message="User not found")
	})
	public ResponseEntity<User> deleteUser(@ApiParam("id user") @PathVariable String id){
		User user = userConfiguration.getUserByIdCode(id);
		userConfiguration.delete(user);
		return ResponseEntity.ok(user);
	}
}
