package com.saaolheart.mumbai.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saaolheart.mumbai.security.UserService;



@Service
public class CustomerService {

	Logger logger = LoggerFactory.getLogger(CustomerService.class);
	
	@Autowired
	private CustomerRepository custRepo;
	
	public List<CustomerDetail> findCustomerByPhoneNo(Long mobileNo) {
		Optional<List<CustomerDetail>> custOption = Optional.of(new ArrayList<CustomerDetail>());
		try {
			custOption = 	custRepo.findByMobileNo(mobileNo);
		}catch(EntityNotFoundException ex) {
			logger.error("Customer with Mobile No"+mobileNo+" does Not exists");			
		}catch(Exception e) {
			logger.error("Customer with Mobile No"+mobileNo+" does Not exists and someting went wrong",e);			
		}
		return custOption.orElse(null);
	}

	public CustomerDetail saveCustomer(CustomerDetail customer) {
		CustomerDetail customerDe = new CustomerDetail();
		try {
			customerDe = custRepo.save(customer);
		}catch(Exception e) {
			logger.error("Customer with Name "+customer.getFirstName()+" could not be saved and something went wrong",e);			
		}
		return customerDe;
	}

	public List<CustomerDetail> findAllCustomerWithSort() {
		
		Optional<List<CustomerDetail>> custOption = Optional.of(new ArrayList<CustomerDetail>());
		try {
			
			custOption = custRepo.findAllByOrderByDateOfCreationDesc();
			
		}catch(Exception ex) {
			logger.error("Unable to find All Customer with sorting on mobile number",ex);
		}
		return custOption.orElse(null);
	}
	
	public CustomerDetail getCustomerDetailById(Long id) {
		
		Optional<CustomerDetail> custOption = Optional.of(new CustomerDetail());
		try {
			
			custOption = custRepo.findById(id);
			
		}catch(Exception ex) {
			logger.error("Unable to find All Customer with sorting on mobile number",ex);
		}
		return custOption.orElse(null);
	}
	
}
