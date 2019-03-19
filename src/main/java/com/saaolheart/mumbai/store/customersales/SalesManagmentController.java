package com.saaolheart.mumbai.store.customersales;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.docx4j.docProps.variantTypes.Array;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saaolheart.mumbai.common.response.ActionResponse;
import com.saaolheart.mumbai.customer.CustomerController;
import com.saaolheart.mumbai.customer.CustomerDetail;
import com.saaolheart.mumbai.customer.CustomerService;
import com.saaolheart.mumbai.invoice.InvoiceDomain;
import com.saaolheart.mumbai.invoice.InvoiceRecieptDetailDomain;
import com.saaolheart.mumbai.invoice.InvoiceStatuses;
import com.saaolheart.mumbai.store.stock.StockDomain;
import com.saaolheart.mumbai.store.stock.StockHistoryDetailsDomain;
import com.saaolheart.mumbai.store.stock.StockManagmentService;

@RestController
@RequestMapping(value="/sales")
@EnableAutoConfiguration
public class SalesManagmentController {


	Logger logger = LoggerFactory.getLogger(SalesManagmentController.class);
	
	@Autowired
	private SalesManagmentServices salesMgmtService;
	
	@Autowired
	private StockManagmentService stockService;
	
	@Autowired
	private CustomerService customerService;
	
	
	/**
	 * Method for Adding New Customers/patients
	 * 
	 * @param customer
	 * @param request
	 * @param userDetails
	 * @param result
	 * @param response
	 * @return
	 */
	@PostMapping(value="/savesales")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ActionResponse<CustomerSalesDomain>> saveCustomer(/* @Valid */ 
			@RequestBody CustomerSalesDomain customerSales,
			HttpServletRequest request,Principal user,
			BindingResult result,HttpServletResponse response){
		
		/**
		 * Get Purchase List
		 * Get Customers to CustomerPurchases
		 * 
		 */
		if(customerSales!=null) {
			/**
			 * Setting of Sales and Invoices
			 */
	
		CustomerDetail customerDb = customerService.findCustomerDetailById(customerSales.getCustomerId());
		if(customerDb!=null && customerDb.getCustomerSalesList() != null && !customerDb.getCustomerSalesList().isEmpty()) {
			
			setSalesAndInvoiceValues(customerSales, user, customerDb);			
			customerDb.getCustomerSalesList().add(customerSales);
		}else {
			setSalesAndInvoiceValues(customerSales, user, customerDb);
			List<CustomerSalesDomain> customerSalesList = new ArrayList<CustomerSalesDomain>();
			customerSalesList.add(customerSales);
			customerDb.setCustomerSalesList(customerSalesList);
		}
		/**
		 * Setting and Updating of Stock value
		 * 
		 */
		customerService.saveCustomer(customerDb);
		
		for(CustomerPurchasesDomain purchase:customerSales.getCustomerPurchasesList()) {
			StockDomain stock = stockService.findStockById(purchase.getStockDomainId());
			Long stockAvailable = stock.getQtyOfStockAvailable() - purchase.getQuantityPurchased();
			Double stockRate = stock.getCurrentRateOfStock();
			Double stockValue = stockRate * stockAvailable;
			if(stock.getStockHistoryDetailsList() != null && !stock.getStockHistoryDetailsList().isEmpty()) {
				
StockHistoryDetailsDomain stockHistoryDetail = new StockHistoryDetailsDomain();
				stockHistoryDetail.setAvailableStock(stockAvailable);
				stockHistoryDetail.setQtyUpdated(purchase.getQuantityPurchased());
				stockHistoryDetail.setStockRate(stockRate);
				stockHistoryDetail.setStockValue(stockValue);
				stockHistoryDetail.setUpdatedOn(new Date());

				stock.getStockHistoryDetailsList().add(stockHistoryDetail);
			}else {
				List<StockHistoryDetailsDomain> stockHistoryDetailList = new ArrayList<>();
				StockHistoryDetailsDomain stockHistoryDetail = new StockHistoryDetailsDomain();
				stockHistoryDetail.setAvailableStock(stockAvailable);
				stockHistoryDetail.setQtyUpdated(purchase.getQuantityPurchased());
				stockHistoryDetail.setStockRate(stockRate);
				stockHistoryDetail.setStockValue(stockValue);
				stockHistoryDetail.setUpdatedOn(new Date());
				stock.setStockHistoryDetailsList(stockHistoryDetailList);
			}
			
			stock.setCurentStockValue(stockValue);
			stock.setQtyOfStockAvailable(stockAvailable);
			stockService.saveStock(stock);
		}
		/**
		 * Save stock and customerDb
		 */
		
		
		
		}else {
			/**
			 * ERROR Message to display No sales List
			 */
		}
		System.out.println(22);
		
		return null;
	}


	private void setSalesAndInvoiceValues(CustomerSalesDomain customerSales, Principal user,
			CustomerDetail customerDb) {
		InvoiceDomain newInvoiceDomain = new InvoiceDomain();
		List<InvoiceRecieptDetailDomain> newInvoiceReciptDomain = new ArrayList<>();
		InvoiceRecieptDetailDomain newRecipt = new InvoiceRecieptDetailDomain();
		
		newRecipt.setIsPrinted(customerSales.getIsPrinted());
		newRecipt.setIsEmailed(customerSales.getIsEmailed());
		newRecipt.setEmailedToId(customerDb.getEmailId());
		newRecipt.setPaymentAmount(customerSales.getPaymentAmount());
		newRecipt.setPaymentDate(new Date());
		newRecipt.setPaymentReferenceNo(customerSales.getPaymentReferenceNo());
		newRecipt.setRecievedBy(user.getName());
		
		newInvoiceReciptDomain.add(newRecipt);			
		newInvoiceDomain.setInvoiceReciptList(newInvoiceReciptDomain);			
		newInvoiceDomain.setBalanceAmt(0D);
		newInvoiceDomain.setBankName(customerSales.getBankName());
		newInvoiceDomain.setGeneretedByName(user.getName());
		newInvoiceDomain.setInvoiceStatus(InvoiceStatuses.PAYMENTDONE.toString());
		newInvoiceDomain.setTotalInvoiceAmt(customerSales.getTotalInvoiceAmt());
		
		customerSales.setInvoiceOfPurchase(newInvoiceDomain);
	}
	
}
