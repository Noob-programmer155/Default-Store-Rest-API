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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.AmrTm.StoreRestAPI.Entity.User;
import com.AmrTm.StoreRestAPI.UserService.UserConfiguration;

@RestController
@RequestMapping("/users")
public class UserRest {
	
	@Autowired
	private UserConfiguration userConfig;
	
	@GetMapping("/user")
	public NavigableSet<User> getUsers(){
		return userConfig.getUsers();
	}
	
	@GetMapping("/user/{id}")
	public EntityModel<User> getUser(@PathVariable String id){
		EntityModel<User> model = EntityModel.of(userConfig.getUserByIdCode(id));
		WebMvcLinkBuilder link = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(UserRest.class).getUsers());
		model.add(link.withRel("all-users"));
		return model;
	}
	
	@PostMapping("/save")
	public ResponseEntity<User> addUser(@RequestBody User user){
		userConfig.save(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getCodeUser()).toUri();
		return ResponseEntity.created(uri).build();
	} 
	
	@PutMapping("/modify/{id}")
	public ResponseEntity<User> modifyUser(@PathVariable String id){
		User user = userConfig.getUserByIdCode(id);
		userConfig.modifyCountIn(user);
		return ResponseEntity.ok(user);
	}
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<User> deleteUser(@PathVariable String id){
		User user = userConfig.getUserByIdCode(id);
		userConfig.delete(user);
		return ResponseEntity.ok(user);
	}
}
