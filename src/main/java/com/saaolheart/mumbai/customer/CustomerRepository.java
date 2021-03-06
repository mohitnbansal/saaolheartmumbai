package com.saaolheart.mumbai.customer;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository  extends JpaRepository<CustomerDetail, Long>{

	Optional<List<CustomerDetail>> findByMobileNo(Long mobileNo);

	Optional<List<CustomerDetail>> findAllByOrderByDateOfCreationDesc();
	
	Optional<List<CustomerDetail>> findByFirstNameContaining(String name);
	
	Optional<List<CustomerDetail>> findByDateOfCreation(Date dateOfCreation);
	
	
}
