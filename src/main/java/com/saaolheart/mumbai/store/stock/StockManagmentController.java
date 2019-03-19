package com.saaolheart.mumbai.store.stock;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saaolheart.mumbai.common.response.ActionResponse;
import com.saaolheart.mumbai.common.response.ActionStatus;
import com.saaolheart.mumbai.customer.CustomerDetail;
import com.saaolheart.mumbai.customer.CustomerService;

@RestController
@RequestMapping(value="/stock")
@EnableAutoConfiguration
@CrossOrigin(origins="*")
public class StockManagmentController {

	Logger logger = LoggerFactory.getLogger(StockManagmentController.class);
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private StockManagmentService stockService;
	
	
	@PostMapping(value="/addstock")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ActionResponse<StockDomain>> createNewStock(/* @Valid */ @RequestBody StockDomain stock,
			HttpServletRequest request,Principal user,
			BindingResult result,HttpServletResponse response){
	
		ActionResponse<StockDomain> actionResponse = new ActionResponse<StockDomain>();
		MultiValueMap<String, String> mMap = new LinkedMultiValueMap<>();
		List<StockHistoryDetailsDomain> stockHistoryDetailsList = new ArrayList<StockHistoryDetailsDomain>();
		StockHistoryDetailsDomain stockHistory = new StockHistoryDetailsDomain();
		stockHistory.setAvailableStock(stock.getQtyOfStockAvailable());
		stockHistory.setIsManualUpdate("YES");
		stockHistory.setLastUpdatedBy(user.getName());
		stockHistory.setQtyUpdated(stock.getQtyOfStockAvailable());
		stockHistory.setStockRate(stock.getCurrentRateOfStock());
		stockHistory.setStockValue(stock.getCurentStockValue());
		stockHistory.setUpdatedOn(new Date());
		stockHistoryDetailsList.add(stockHistory);
		stock.setStockHistoryDetailsList(stockHistoryDetailsList);
		stock.setAddedOn(new Date());
		stock.setLastUpdatedBy(user.getName());
		stock.setLastUpdatedOn(new Date());
		
		StockDomain stockDb = null;
		stock =	stockService.saveStock(stock);
		
		actionResponse.setDocument(stock);
		actionResponse.setActionResponse(ActionStatus.SUCCESS);
		return new ResponseEntity<ActionResponse<StockDomain>>(actionResponse,mMap,HttpStatus.OK);
	}
	
	@GetMapping(value="/getStockCategory")
	public ResponseEntity<List<StockCategoryDomain>> getDetail(HttpServletRequest request,Principal user,
			HttpServletResponse response) {
		return new ResponseEntity<List<StockCategoryDomain>>(stockService.getAllStockCategory(),HttpStatus.OK);
	}
	
	@GetMapping(value="/getAllStockList")
	public ResponseEntity<List<StockDomain>> getAllStockList(HttpServletRequest request,Principal user,
			HttpServletResponse response) {
		return new ResponseEntity<List<StockDomain>>(stockService.getAllStockList(),HttpStatus.OK);
	}
	

	@GetMapping(value="/getstockbysearch")
	public ResponseEntity<List<StockDomain>> getStockDomainBySearch(@RequestParam("searchParam") String searchParam,HttpServletRequest request,Principal user,
			HttpServletResponse response){
		return new ResponseEntity<List<StockDomain>>(stockService.findByStockNameContaining(searchParam),HttpStatus.OK);
	}
	
	
	
}
	
