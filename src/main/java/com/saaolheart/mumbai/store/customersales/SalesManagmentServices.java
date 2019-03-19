package com.saaolheart.mumbai.store.customersales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SalesManagmentServices {

	@Autowired
	private CustomerPurchasesRepo purchasesRepo;
	
	@Autowired
	private CustomerSalesRepo salesRepo;
	
	
}
