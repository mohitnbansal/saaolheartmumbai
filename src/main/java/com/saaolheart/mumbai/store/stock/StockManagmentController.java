package com.saaolheart.mumbai.store.stock;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.saaolheart.mumbai.customer.CustomerService;
import com.saaolheart.mumbai.store.customersales.CustomerPurchasesDomain;

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
		Set<String> mMap = new HashSet<>();
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
		mMap.add("Successfully Added Stock !");
		actionResponse.setDocument(stock);
		actionResponse.setActionResponse(ActionStatus.SUCCESS);
		actionResponse.setError(mMap);
		return new ResponseEntity<ActionResponse<StockDomain>>(actionResponse,HttpStatus.OK);
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
	
	
	@GetMapping(value="/getstockdetail/{id}")
	public ResponseEntity<StockDomain> getStockDetailsById(@PathVariable("id") Long id,HttpServletRequest request,Principal user,
			HttpServletResponse response){
		return new ResponseEntity<StockDomain>(stockService.findStockById(id),HttpStatus.OK);
	}
	
	@GetMapping(value="/getsalesstockbydetails/{id}")
	public ResponseEntity<List<CustomerPurchasesDomain>> getSalesOfStockDetailsById(@PathVariable("id") Long id,HttpServletRequest request,Principal user,
			HttpServletResponse response){
		return new ResponseEntity<List<CustomerPurchasesDomain>>(stockService.findAllPurchasesForStock(id),HttpStatus.OK);
	}
	
	@PostMapping(value="/updatestock/{id}")
	public ResponseEntity<ActionResponse<StockDomain>> updateStock(/* @Valid */@PathVariable("id") Long id, @RequestBody StockDomain stock,
			HttpServletRequest request,Principal user,
			BindingResult result,HttpServletResponse response){
		ActionResponse<StockDomain> actionResponse = new ActionResponse<StockDomain>();
		Set<String> errAndMsg  = new HashSet<String>();
		Long newStockQty = null;
		Double newStockValue = null;
		StockDomain savedStock = null;
		if(stock !=null && stock.getId()!=null) {
			StockDomain stockFromDb = stockService.findStockById(stock.getId());
			
				
//				if(stock.getQtyOfStockToUpdate()!=null && stock.getQtyOfStockToUpdate() >0 ) {
					newStockQty = stockFromDb.getQtyOfStockAvailable() + stock.getQtyOfStockToUpdate();
//				}else {
//					newStockQty = stockFromDb.getQtyOfStockAvailable();
//				}
				
				newStockValue = stock.getCurrentRateOfStock() * newStockQty;
				stockFromDb.setCurrentRateOfStock(stock.getCurrentRateOfStock());
				stockFromDb.setQtyOfStockAvailable(newStockQty);
			
				if(stockFromDb.getStockHistoryDetailsList() != null) {					
					StockHistoryDetailsDomain history = new StockHistoryDetailsDomain();
					history.setAvailableStock(newStockQty);
					history.setIsManualUpdate("YES");
					history.setLastUpdatedBy(user.getName());
					history.setQtyUpdated(stock.getQtyOfStockToUpdate().longValue());
					history.setReasonForUpdate(stock.getReasonForUpdate());
					history.setStockValue(newStockValue);
					history.setStockRate(stock.getCurrentRateOfStock());
					history.setUpdatedOn(new Date());
					stockFromDb.getStockHistoryDetailsList().add(history);
				}else {
					List<StockHistoryDetailsDomain> stockHstryList = new ArrayList<StockHistoryDetailsDomain>();
					
					StockHistoryDetailsDomain history = new StockHistoryDetailsDomain();
					history.setAvailableStock(newStockQty);
					history.setIsManualUpdate("YES");
					history.setLastUpdatedBy(user.getName());
					history.setQtyUpdated(stock.getQtyOfStockToUpdate().longValue());
					history.setReasonForUpdate(stock.getReasonForUpdate());
					history.setStockValue(newStockValue);
					history.setStockRate(stock.getCurrentRateOfStock());
					history.setUpdatedOn(new Date());
				
					stockHstryList.add(history);
					stockFromDb.setStockHistoryDetailsList(stockHstryList);
				}
			 savedStock = stockService.saveStock(stockFromDb);
			actionResponse.setDocument(savedStock);
			errAndMsg.add("Successfully Updated Stock!");
			actionResponse.setError(errAndMsg);
		}else {
			
			actionResponse.setDocument(savedStock);
			errAndMsg.add("Unable to update Stock Details!");
			actionResponse.setError(errAndMsg);
		}
		
		
		return new ResponseEntity<ActionResponse<StockDomain>>(actionResponse,HttpStatus.OK);
	}
	
}
	
