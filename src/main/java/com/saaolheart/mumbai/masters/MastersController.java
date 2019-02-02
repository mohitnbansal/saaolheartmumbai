package com.saaolheart.mumbai.masters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saaolheart.mumbai.masters.invoice.InvoiceMasterService;
import com.saaolheart.mumbai.masters.invoice.InvoiceTypeMaster;

@RestController
@RequestMapping("master")
public class MastersController {
	
	
	/**
	 * INVOICE MASTERS Block
	 * 
	 */
	
	@Autowired
	private InvoiceMasterService invoiceMasterService;
	
	
	
	@GetMapping("invoicemaster")
	public ResponseEntity<List<InvoiceTypeMaster>> getAllInvoiceMasterType(){
		
		List<InvoiceTypeMaster> masterList = invoiceMasterService.getAllInvoiceMasterTypes();		
		
		return new ResponseEntity<List<InvoiceTypeMaster>>(masterList,HttpStatus.OK);
	}

}
