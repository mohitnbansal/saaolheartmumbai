package com.saaolheart.mumbai.store.customersales;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saaolheart.mumbai.common.response.ActionResponse;
import com.saaolheart.mumbai.common.utility.PdfGenerator;
import com.saaolheart.mumbai.customer.CustomerDetail;
import com.saaolheart.mumbai.customer.CustomerService;
import com.saaolheart.mumbai.invoice.InvoiceDomain;
import com.saaolheart.mumbai.invoice.InvoiceRecieptDetailDomain;
import com.saaolheart.mumbai.invoice.InvoiceStatuses;
import com.saaolheart.mumbai.store.stock.StockDomain;
import com.saaolheart.mumbai.store.stock.StockHistoryDetailsDomain;
import com.saaolheart.mumbai.store.stock.StockManagmentService;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

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
		ActionResponse<CustomerSalesDomain> actionResponse = new ActionResponse<CustomerSalesDomain>();
		Set<String> errMsg  = new  HashSet<String>();
//		CustomerSalesDomain savedSales = null;
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
				stockHistoryDetail.setReasonForUpdate("NEW");
				stockHistoryDetail.setIsManualUpdate("NO");
				stockHistoryDetail.setUpdatedOn(new Date());
				stockHistoryDetail.setLastUpdatedBy(user.getName());
				stock.getStockHistoryDetailsList().add(stockHistoryDetail);
			}else {
				List<StockHistoryDetailsDomain> stockHistoryDetailList = new ArrayList<>();
				StockHistoryDetailsDomain stockHistoryDetail = new StockHistoryDetailsDomain();
				stockHistoryDetail.setAvailableStock(stockAvailable);
				stockHistoryDetail.setQtyUpdated(purchase.getQuantityPurchased());
				stockHistoryDetail.setStockRate(stockRate);
				stockHistoryDetail.setStockValue(stockValue);
				stockHistoryDetail.setUpdatedOn(new Date());
				stockHistoryDetail.setReasonForUpdate("NEW");
				stockHistoryDetail.setIsManualUpdate("NO");
				stockHistoryDetail.setLastUpdatedBy(user.getName());
				stockHistoryDetailList.add(stockHistoryDetail);
				stock.setStockHistoryDetailsList(stockHistoryDetailList);
			}
			
			stock.setCurentStockValue(stockValue);
			stock.setQtyOfStockAvailable(stockAvailable);
			
			
			stockService.saveStock(stock);
			
		}
		
		/**
		 * Save stock and customerDb
		 */
		
		errMsg.add("Succesfully Created Sales/Invoice Record");
		actionResponse.setError(errMsg);
//		actionResponse.setDocument(document);
		}else {
			/**
			 * ERROR Message to display No sales List
			 */
		}
//		System.out.println(22);
		
		return new ResponseEntity<ActionResponse<CustomerSalesDomain>>(actionResponse,HttpStatus.OK);
	}

	@GetMapping(value="/getallsales")
	public ResponseEntity<ActionResponse<List<CustomerSalesDomain>>> getAllSalesList(/* @Valid */
			HttpServletRequest request,Principal user,HttpServletResponse response){
		ActionResponse<List<CustomerSalesDomain>> actionResponse = new ActionResponse<List<CustomerSalesDomain>>();
		List<CustomerSalesDomain> salesListFromDb = salesMgmtService.getAllSalesList();
		actionResponse.setDocument(salesListFromDb);		
		return new ResponseEntity<ActionResponse<List<CustomerSalesDomain>>>(actionResponse,HttpStatus.OK);
	}

	@GetMapping(value="/findsalesbyid/{id}")
	public ResponseEntity<ActionResponse<CustomerSalesDomain>> getSalesById(@PathVariable("id") Long id,HttpServletRequest request,Principal user,
			HttpServletResponse response){
		
		ActionResponse<CustomerSalesDomain> actionResponse = new ActionResponse<CustomerSalesDomain>();
		CustomerSalesDomain salesDb = salesMgmtService.findCustomerSaledById(id);
		actionResponse.setDocument(salesDb);
		return new ResponseEntity<ActionResponse<CustomerSalesDomain>>(actionResponse,HttpStatus.OK);

	}
	
	@PostMapping(value="/updatesales")
	public ResponseEntity<ActionResponse<CustomerSalesDomain>> updateSales(@RequestBody List<CustomerPurchasesDomain> customerPurchesList, HttpServletRequest request,Principal user,
			HttpServletResponse response){
		ActionResponse<CustomerSalesDomain> actionResponse = new ActionResponse<CustomerSalesDomain>();
		Set<String> errAndMsg = new HashSet<String>();
		
		if(customerPurchesList!=null && !customerPurchesList.isEmpty() && customerPurchesList.get(0).getCustomerSaledId()!=null) {
		CustomerSalesDomain salesDb = salesMgmtService.findCustomerSaledById(customerPurchesList.get(0).getCustomerSaledId());
		InvoiceDomain invoiceDb = salesDb.getInvoiceOfPurchase();
		Double refundAmount = 0D;
		/**
		 * Need to change the below way
		 */
//		UPDATED Purchases List.
		for(CustomerPurchasesDomain purchaseDB:salesDb.getCustomerPurchasesList()) {
			for(CustomerPurchasesDomain purchaseView:customerPurchesList) {
				if(purchaseView.getId()==purchaseDB.getId()) {
					
					//Cancel the Original
//					purchaseDB.setIsCancelled("CANCELLED");		
					//Making of new purchase with new QTY and updated Price from Frontend.
					if(purchaseView.getQuantityPurchased().compareTo(purchaseDB.getQuantityPurchased())!=0)
					{
					Long differenceQty = purchaseDB.getQuantityPurchased() - purchaseView.getQuantityPurchased();
					Double newPrice =  purchaseView.getQuantityPurchased() * purchaseView.getRateOfStock();
//					CustomerPurchasesDomain newPurchases = new CustomerPurchasesDomain();
//					newPurchases.setCustomerSaledId(purchaseView.getCustomerSaledId());
//					newPurchases.setStockDomainId(purchaseView.getStockDomainId());
//					newPurchases.setQuantityPurchased(purchaseView.getQuantityPurchased());
//					newPurchases.setRateOfStock(purchaseView.getRateOfStock());
//					newPurchases.setPrice(purchaseView.getPrice());
					purchaseDB.setQuantityPurchased(purchaseView.getQuantityPurchased());
					purchaseDB.setPrice(newPrice);
					purchaseDB.setIsCancelled("MODIFIED");
//					salesDb.getCustomerPurchasesList().add(newPurchases);
					
					/**
					 * UPDATING STOCK IN BELOW METHOD
					 */

					StockDomain stockFromDb = stockService.findStockById(purchaseDB.getStockDomainId());
					refundAmount = refundAmount + differenceQty*purchaseView.getRateOfStock();
					Long stockQtyAvailable = stockFromDb.getQtyOfStockAvailable() - differenceQty;
					Double stockValue = stockFromDb.getCurentStockValue() + differenceQty*purchaseView.getRateOfStock();
//					stockFromDb.getCurrentRateOfStock();
					Double purchaseRate = 	purchaseView.getRateOfStock();
					stockFromDb.setCurentStockValue(stockValue);
					stockFromDb.setQtyOfStockAvailable(stockQtyAvailable);
					StockHistoryDetailsDomain newStockHistory = new StockHistoryDetailsDomain();
					newStockHistory.setQtyUpdated(differenceQty);
					newStockHistory.setStockRate(purchaseView.getRateOfStock());
					newStockHistory.setAvailableStock(stockQtyAvailable);
					newStockHistory.setStockValue(stockValue);
					newStockHistory.setReasonForUpdate("REFUND_RCVD");
					newStockHistory.setIsManualUpdate("NO");
					newStockHistory.setUpdatedOn(new Date());
					newStockHistory.setLastUpdatedBy(user.getName());
					newStockHistory.setStockId(purchaseView.getStockDomainId());
					stockFromDb.getStockHistoryDetailsList().add(newStockHistory);
					stockService.saveStock(stockFromDb);
					}
				break;
				}				
			}
		}
		Double invoiceAmtNew = invoiceDb.getTotalInvoiceAmt() - refundAmount;
		invoiceDb.setTotalInvoiceAmt(invoiceAmtNew);
		invoiceDb.getInvoiceReciptList().get(0).setPaymentAmount(invoiceAmtNew);
		invoiceDb.getInvoiceReciptList().get(0).setRefundAmount(refundAmount);
		CustomerSalesDomain salesSaved = salesMgmtService.savaSales(salesDb);
		actionResponse.setDocument(salesSaved);
		errAndMsg.add("Succesfull Update Sales and Stock!");
		actionResponse.setError(errAndMsg);
		}
		else {
			errAndMsg.add("Unable to find Sales and Purchases!");
			actionResponse.setError(errAndMsg);
			return new ResponseEntity<ActionResponse<CustomerSalesDomain>>(actionResponse,HttpStatus.BAD_REQUEST);

		}	
		return new ResponseEntity<ActionResponse<CustomerSalesDomain>>(actionResponse,HttpStatus.OK);
	}
	
	
	@GetMapping(value="/printRecipt")
	public ResponseEntity<Resource> printRecipt() throws IOException, XDocReportException, Docx4JException {
		
		  
//
		 String templatePath = "/home/mohit/ProtechnicWorkspace/SaaolHearts/Project/Root/saaolheartmumbai/ThankYouNote_Template.docx";
		  
		  Map<String, Object> nonImageVariableMap = new HashMap<String, Object>();
		  nonImageVariableMap.put("thank_you_date", "24-June-2013");
		  nonImageVariableMap.put("name", "Rajani Jha");
		  nonImageVariableMap.put("website", "www.sambhashanam.com");
		  nonImageVariableMap.put("author_name", "Dhananjay Jha"); Map<String, String>
		  imageVariablesWithPathMap =new HashMap<String, String>();
		  imageVariablesWithPathMap.put("header_image_logo","/home/mohit/ProtechnicWorkspace/SaaolHearts/Project/Root/saaolheartmumbai/im.png");
		  
		  byte[] mergedOutput =  PdfGenerator.mergeAndGeneratePDFOutput(templatePath, TemplateEngineKind.Velocity, nonImageVariableMap, imageVariablesWithPathMap); 
//		  FileOutputStream os = new FileOutputStream("/home/mohit/ProtechnicWorkspace/SaaolHearts/Project/Root/saaolheartmumbai/"+System.nanoTime()+".pdf");
//		  os.write(mergedOutput);
		  ByteArrayResource resource = new ByteArrayResource(mergedOutput);
//		  os.flush();
//		  os.close();
		  
		  HttpHeaders header = new HttpHeaders();
		  header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+resource.getFile().getName());
		  header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		  header.add("Pragma", "no-cache");
		  header.add("Expires", "0");
		  return ResponseEntity.ok()
                .headers(header)
                .contentLength(resource.getFile().length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
//		File file = new File(SERVER_LOCATION + File.separator + image + EXTENSION);
//
//        HttpHeaders header = new HttpHeaders();
//        header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=img.jpg");
//        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
//        header.add("Pragma", "no-cache");
//        header.add("Expires", "0");
//
//        Path path = Paths.get(file.getAbsolutePath());
//        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));
//
//        return ResponseEntity.ok()
//                .headers(header)
//                .contentLength(file.length())
//                .contentType(MediaType.parseMediaType("application/octet-stream"))
//                .body(resource);
    }

	
	@GetMapping(value="/emailRecipt")
	public void emailRecipt() {
		
	}
	
	private void setSalesAndInvoiceValues(CustomerSalesDomain customerSales, Principal user,
			CustomerDetail customerDb) {
		
		/*
		 * INVOICE TYPE ID IS NOT GETTING SET
		 */
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
		newInvoiceDomain.setCreatedDate(new Date());
		customerSales.setInvoiceOfPurchase(newInvoiceDomain);
	}
	
}
