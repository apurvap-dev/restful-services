package com.example.restful.microservices.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
	
	private static List<User> listOfUser = new ArrayList<User>();
	
	private static int maxUserId = 3;
	
	static {
		listOfUser.add(new User(0,"Apurva", new Date()));
		listOfUser.add(new User(1,"Nidhi", new Date()));
		listOfUser.add(new User(2,"Tejas", new Date()));
	}
	
	public List<User> findAll() {
		return listOfUser;
	}
	
	public User save(User user) {
		if(user.getId() == null) {
			user.setId(++maxUserId);
		}
		listOfUser.add(user);
		return user;
	}
	
	public User findOne(int id) {
		return listOfUser.stream().filter(e -> e.getId() ==  id).findFirst().orElse(null);
	}
	
	public User deleteById(int id) {
		User user = findOne(id);
		if(user !=null) listOfUser.remove(user);
		return user;
	}

}
