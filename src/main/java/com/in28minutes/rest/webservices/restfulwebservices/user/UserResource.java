package com.in28minutes.rest.webservices.restfulwebservices.user;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.in28minutes.rest.webservices.restfulwebservices.exception.UserNotFoundException;

@RestController
public class UserResource {
	
	@Autowired
	private UserDaoService service;
	
	
	@GetMapping(path="/users")
	public List<User> retrieveAllUsers(){
		return service.findAll();
	}
	
	@GetMapping(path="/users/{id}")
	public Optional<User> retrieveUser(@PathVariable Integer id){
		Optional<User> user = service.findOne(id);
		if(user.isEmpty()) {
			throw new UserNotFoundException("id-" + id);
		}
		return user;
				
	}
	
	@PostMapping(path="/users")
	public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
		User savedUser = service.save(user);
		
		URI uriLocation = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		return ResponseEntity.created(uriLocation).build();
	}
	
	@DeleteMapping(path="/users/{id}")
	public void deleteUser(@PathVariable Integer id){
		Optional<User> user = service.deleteById(id);
		if (user.isEmpty()) {
			throw new UserNotFoundException("id - " + id);
		}
	}
}
