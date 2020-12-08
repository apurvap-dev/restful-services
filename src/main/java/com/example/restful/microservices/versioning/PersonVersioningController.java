package com.example.restful.microservices.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonVersioningController {

	@GetMapping("v1/person")
	public PersonV1 getPersonV1() {
		return new PersonV1("charlie");
	}
	
	@GetMapping("v2/person")
	public PersonV2 getPersonV2() {
		return new PersonV2(new Name("charlie","bob"));
	}
	//url version 
	@GetMapping(value = "person/param",params = "version-1")
	public PersonV1 getPersonParams1() {
		return new PersonV1("charlie");
	}
	
	@GetMapping(value = "person/param",params = "version-2")
	public PersonV2 getPersonParams2() {
		return new PersonV2(new Name("charlie","bob"));
	}
	//header versioning
	@GetMapping(value="person/header", headers = "X-API-VERSION=1")
	public PersonV1 headerV1() {
		return new PersonV1("charlie");
	}
	
	@GetMapping(value="person/header", headers = "X-API-VERSION=2")
	public PersonV2 headerV2() {
		return new PersonV2(new Name("charlie","bob"));
	}
	//media type versioning
	@GetMapping(value= "person/produces", produces =  "application/vnd.company.app-v1+json")
	public PersonV1 producesV1() {
		return new PersonV1("charlie");
	}
	
	@GetMapping(value= "person/produces", produces =  "application/vnd.company.app-v2+json")
	public PersonV2 producesV2() {
		return new PersonV2(new Name("charlie","bob"));
	}
}
