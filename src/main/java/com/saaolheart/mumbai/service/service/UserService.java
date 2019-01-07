package com.saaolheart.mumbai.service.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.saaolheart.mumbai.domain.security.Role;
import com.saaolheart.mumbai.domain.security.User;
import com.saaolheart.mumbai.repository.security.UserRepository;

/**
 * Created by ala on 17.5.16.
 */

@Service(value = "userService")
public class UserService implements UserDetailsService {
	
	
	@Autowired
	private UserRepository userDao;

	@Override
	public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
		User user = userDao.findByUsername(userId);
		if(user == null){
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		 Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
	        for(Role role: user.getRoles()){
	            authorities.add(new SimpleGrantedAuthority(role.getDescription().toString()));
	            
	        }
	        UserDetails userDetails = new org.springframework.security.core.userdetails.
	                User(user.getUsername(), user.getPassword(), authorities);
		return userDetails;
	}

	/*
	 * private List<SimpleGrantedAuthority> getAuthority() { return
	 * Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN")); }
	 */

	public List<User> findAll() {
		List<User> list = new ArrayList<User>();
		userDao.findAll().iterator().forEachRemaining(list::add);
		return list;
	}

	public User save(User user) {
		
		return userDao.save(user);
	}
	
}