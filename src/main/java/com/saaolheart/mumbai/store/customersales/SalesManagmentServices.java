package com.saaolheart.mumbai.store.customersales;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saaolheart.mumbai.customer.CustomerDetail;
import com.saaolheart.mumbai.customer.CustomerRepository;
import com.saaolheart.mumbai.store.stock.StockDomain;

@Service
public class SalesManagmentServices {

	
	Logger logger = LoggerFactory.getLogger(SalesManagmentServices.class);

	
	@Autowired
	private CustomerPurchasesRepo purchasesRepo;
	
	@Autowired
	private CustomerSalesRepo salesRepo;
	
	@Autowired
	private CustomerRepository custRepo;

	public List<CustomerSalesDomain> getAllSalesList() {
		Optional<List<CustomerSalesDomain>> salesList = Optional.of(new ArrayList<CustomerSalesDomain>());
		try {
		salesList =salesRepo.findByOrderByIdDesc();
		if(salesList.isPresent()) {
			for(CustomerSalesDomain sales:salesList.get()) 
			{
				CustomerDetail cust = custRepo.findById(sales.getCustomerId()).get();
				sales.setCustomerName(cust.getFirstName()+ " " + cust.getLastName());
			}
		}
		}
		catch(Exception e) {
			logger.error("ERROR in Finding Sales List");
		}
		return salesList.orElse(null);
	}

	public CustomerSalesDomain findCustomerSaledById(Long id) {
	Optional<CustomerSalesDomain> fromDb = Optional.of(new CustomerSalesDomain());
	try {
		fromDb = salesRepo.findById(id);
		if(fromDb.isPresent()) {
			{
				CustomerDetail cust = custRepo.findById(fromDb.get().getCustomerId()).get();
				fromDb.get().setCustomerName(cust.getFirstName()+ " " + cust.getLastName());
			}
		}
	}catch(Exception e) {
		logger.error("Error in finding Sales Domain by ID"+ id);
	}
		return fromDb.orElse(null);
	}

	public CustomerSalesDomain savaSales(CustomerSalesDomain salesDb) {
		CustomerSalesDomain salesSave = new CustomerSalesDomain();
		try {
			salesSave = salesRepo.save(salesDb);
		}
		catch(Exception e) {
			logger.error("Unable to Save Customer Sales for Sales ID"+salesDb.getId());
		}
		return salesSave;
	}

	
	
}
