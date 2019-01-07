package com.saaolheart.mumbai.repository.security;

import org.springframework.data.jpa.repository.JpaRepository;

import com.saaolheart.mumbai.domain.security.User;

public interface UserRepository extends JpaRepository<User, String>  {
	
	 User findByUsername(String username);
	    Boolean existsByUsername(String username);
	    Boolean existsByEmail(String email);
	
	
}
