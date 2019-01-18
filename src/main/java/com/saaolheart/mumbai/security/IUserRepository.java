package com.saaolheart.mumbai.security;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saaolheart.mumbai.security.domain.User;

@Repository(value = "userRepository")
public interface IUserRepository extends JpaRepository<User, String>  {
	
	 User findByUsername(String username);
	    Boolean existsByUsername(String username);
	    Boolean existsByEmail(String email);
	
	
}
