package com.saaolheart.mumbai.store.stock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saaolheart.mumbai.customer.CustomerRepository;
import com.saaolheart.mumbai.invoice.InvoiceRepository;


@Service
public class StockManagmentService {
	

	Logger logger = LoggerFactory.getLogger(StockManagmentService.class);
	
	@Autowired
	private CustomerRepository custRepo;
	
	
	@Autowired
	private InvoiceRepository invoiceRepo;
	
	@Autowired
    private	StockCategoryRepo stockCategoryRepo;

	
	@Autowired
	private StockRepo stockRepo;
	
	@Autowired
	private StockHistoryRepo stockHistoryDetailRepo;
	
	public StockDomain saveStock(StockDomain stock) {
		
		try {
			return stockRepo.save(stock);
		}catch(Exception e) {
			return null;
		}
	}
	
	public List<StockCategoryDomain> getAllStockCategory(){
		return stockCategoryRepo.findAll();
	}

	public List<StockDomain> getAllStockList() {
		// TODO Auto-generated method stub
		return stockRepo.findAllByOrderByAddedOnDesc();
	}

	public List<StockDomain> findByStockNameContaining(String searchParam) {
		
			Optional<List<StockDomain>> customerList = Optional.of(new  ArrayList<StockDomain>());
		
			try {
				
				customerList = stockRepo.findByStockNameContaining(searchParam);
			}
			catch(Exception e) {
				logger.error("Could Not Find any customer with search param "+searchParam,e);			
					
			}
			return customerList.orElseGet(null);
		
	}

	public StockDomain findStockById(Long stockDomainId) {
	Optional<StockDomain> stock = Optional.of(new StockDomain());
	try {
		stock = stockRepo.findById(stockDomainId);
	}catch(Exception e){
		logger.error("Could Not Find any customer with search param "+stockDomainId,e);			
	}
		return stock.orElse(null);
	}
	
}
