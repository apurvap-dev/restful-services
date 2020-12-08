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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping(path = "jpa")
public class UserJPAResource {
	
	private final UserDaoService userDaoService;
	private final MessageSource messageSource;
	private final UserRepository userRepo;
	
	public UserJPAResource(UserDaoService userDaoService, MessageSource messageSource, UserRepository userRepo) {
		this.userDaoService = userDaoService;
		this.messageSource =  messageSource;
		this.userRepo =  userRepo;
	}
	
	@GetMapping(path = "/users")
	public List<User> retrieveAllUsers() {
		
		return userRepo.findAll();
		
	}
	@GetMapping(path = "/users/{id}")
	public User retrieveUser(@PathVariable int id) {
		User findOne = userRepo.findById(id).orElse(null);
		if(findOne ==  null) throw new UserNotFoundException(String.valueOf(id));
		return findOne;
	}

	@PostMapping(path= "/users")
	public EntityModel<User> createUser(@Valid @RequestBody User user) {
		User savedUser = userRepo.save(user);
		
		//URI location = ServletUriComponentsBuilder.fromCurrentRequest()
		//.path("/{id}").buildAndExpand(savedUser.getId()).toUri();
		//return ResponseEntity.created(location).build();
		
		EntityModel<User> resource = EntityModel.of(user);
		WebMvcLinkBuilder linkTo = WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(this.getClass()).retrieveAllUsers());
		resource.add(linkTo.withRel("all-users"));
		return resource;
	}
	
	@DeleteMapping(path= "/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userRepo.deleteById(id);
	}
	
	@GetMapping(path = "/test/internationalmessage") 
	public String returnMessage(){
		//you can pass through @RequestHeader(name="Accept-language",required= false) Locale locale then 
		//you need to configure SessionLocaleResolver in main class  
		return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
	}
}
