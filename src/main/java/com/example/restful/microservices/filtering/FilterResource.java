package com.example.restful.microservices.filtering;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FilterResource {

	@GetMapping(path = "/filterBean")
	public FilterBean getFilterBeanStatic() {
		FilterBean filterBean = new FilterBean("1", "2", "3");
		return filterBean;
	}
	
	
	@GetMapping(path = "/filterBeanDynamic")
	public FilterBean getFilterBeanDynamic() {
		FilterBean filterBean = new FilterBean("1", "2", "3");
		return filterBean;
	}
}
