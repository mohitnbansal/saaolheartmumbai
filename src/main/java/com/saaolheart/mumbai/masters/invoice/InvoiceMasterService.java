package com.saaolheart.mumbai.masters.invoice;

import java.security.cert.PKIXRevocationChecker.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InvoiceMasterService {

	
	@Autowired
	private InvoiceTypeMasterRepo invoiceMasterRepo;

	public List<InvoiceTypeMaster> getAllInvoiceMasterTypes() {
		// TODO Auto-generated method stub
		
		List<InvoiceTypeMaster> list = new ArrayList<InvoiceTypeMaster>();
		list = invoiceMasterRepo.findAll();
		return list;
	}
	
	
}
