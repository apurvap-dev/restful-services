package com.example.restful.microservices.user;

import java.net.URI;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.hibernate.validator.spi.messageinterpolation.LocaleResolverContext;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {
	
	private final UserDaoService userDaoService;
	private final MessageSource messageSource;
	
	public UserResource(UserDaoService userDaoService, MessageSource messageSource) {
		this.userDaoService = userDaoService;
		this.messageSource =  messageSource;
	}
	
	@GetMapping(path = "/users")
	public List<User> retrieveAllUsers() {
		
		return userDaoService.findAll();
		
	}
	@GetMapping(path = "/users/{id}")
	public User retrieveUser(@PathVariable int id) {
		User findOne = userDaoService.findOne(id);
		if(findOne ==  null) throw new UserNotFoundException(String.valueOf(id));
		return findOne;
	}

	@PostMapping(path= "/users")
	public EntityModel<User> createUser(@Valid @RequestBody User user) {
		User savedUser = userDaoService.save(user);
		
		//URI location = ServletUriComponentsBuilder.fromCurrentRequest()
		//.path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		//return ResponseEntity.created(location).build();
		
		EntityModel<User> resource = EntityModel.of(user);
		WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
		resource.add(linkTo.withRel("all-users"));
		return resource;
	}
	
	@DeleteMapping(path= "/users/{id}")
	public ResponseEntity<Object> deleteUser(@PathVariable int id) {
		User savedUser = userDaoService.deleteById(id);
		if(savedUser ==  null) throw new UserNotFoundException(String.valueOf(id));
		return ResponseEntity.accepted().build();
	}
	
	@GetMapping(path = "/test/internationalmessage") 
	public String returnMessage(){
		//you can pass through @RequestHeader(name="Accept-language",required= false) Locale locale then 
		//you need to configure SessionLocaleResolver in main class  
		return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
	}
}
