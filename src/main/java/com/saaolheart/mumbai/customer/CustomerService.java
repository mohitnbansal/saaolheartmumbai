package com.saaolheart.mumbai.customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.saaolheart.mumbai.invoice.InvoiceDomain;
import com.saaolheart.mumbai.invoice.InvoiceRepository;
import com.saaolheart.mumbai.masters.invoice.InvoiceTypeMaster;
import com.saaolheart.mumbai.masters.invoice.InvoiceTypeMasterRepo;
import com.saaolheart.mumbai.masters.treatment.TreatmentTypeMasterDomain;
import com.saaolheart.mumbai.masters.treatment.TreatmentTypeMasterRepo;
import com.saaolheart.mumbai.treatment.ctangiography.CtAngioDetailRepo;
import com.saaolheart.mumbai.treatment.ctangiography.CtAngioDetailsDomain;
import com.saaolheart.mumbai.treatment.doctorconsultation.DoctorConsultationDomain;
import com.saaolheart.mumbai.treatment.doctorconsultation.DoctorConsultationRepository;
import com.saaolheart.mumbai.treatment.treatmentplan.TreatmentPlanDetailDomain;
import com.saaolheart.mumbai.treatment.treatmentplan.TreatmentPlanDetailsRepo;
import com.saaolheart.mumbai.treatment.treatmentplan.TreatmentPlanDomain;
import com.saaolheart.mumbai.treatment.treatmentplan.TreatmentPlanRepository;



@Service
public class CustomerService {

	Logger logger = LoggerFactory.getLogger(CustomerService.class);
	
	@Autowired
	private CustomerRepository custRepo;
	
	@Autowired
	private DoctorConsultationRepository docRepo;
	
	@Autowired
	private InvoiceRepository invoiceRepo;
	
	@Autowired
	private CtAngioDetailRepo ctAngioRepo;
	
	@Autowired
	private TreatmentPlanRepository treatmentRepo;
	
	@Autowired
	private TreatmentPlanDetailsRepo treatmentPlanDetailRepo;
	
	
	@Autowired
	private TreatmentTypeMasterRepo treatmentTypeMasterRepo;
	
	@Autowired
	private InvoiceTypeMasterRepo invoiceTypeMaster;
	
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
	
	public CustomerDetail findCustomerDetailById(Long id) {
		
		Optional<CustomerDetail> custOption = Optional.of(new CustomerDetail());
		try {
			
			custOption = custRepo.findById(id);
			
		}catch(Exception ex) {
			logger.error("Unable to find All Customer with sorting on mobile number",ex);
		}
		return custOption.orElse(null);
	}
	
	/**
	 * 
	 * Methods Related to Doctor Consultation Domain
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public DoctorConsultationDomain saveDoctorDetails(DoctorConsultationDomain customer) {
		DoctorConsultationDomain customerDe = new DoctorConsultationDomain();
		try {
			customerDe = docRepo.save(customer);
			docRepo.refresh(customerDe);
		}catch(Exception e) {
			logger.error("Doctor details for cutomer id "+customer.getCustomerId()+" could not be saved and something went wrong",e);			
		}
		return customerDe;
	}

	public InvoiceDomain findInvoiceDomainById(Long id) {
		
		Optional<InvoiceDomain> invFrDb = Optional.of(new InvoiceDomain());
		try {
			invFrDb = invoiceRepo.findById(id);
		}catch(Exception e) {
			logger.error("Invoice details for invoice id "+id+" could not find and something went wrong",e);			
		}
		return invFrDb.orElse(null);
	}
  
	public InvoiceDomain saveinvoiceDomain(InvoiceDomain inv) {
		InvoiceDomain invFrDb = null;
		try {
			invFrDb = invoiceRepo.save(inv);
			invoiceRepo.refresh(invFrDb);
		}catch(Exception e) {
			logger.error("Invoice details for invoice id "+inv.getId()+" could not find and something went wrong",e);			
		}
		return invFrDb;
	}

	public CtAngioDetailsDomain saveCtAngioDetails(CtAngioDetailsDomain ctAngioDetails) {
		CtAngioDetailsDomain customerDe = new CtAngioDetailsDomain();
	try {
		customerDe = ctAngioRepo.save(ctAngioDetails);
		ctAngioRepo.refresh(customerDe);
	}catch(Exception e) {
		logger.error("Ct Angio details for cutomer id "+ctAngioDetails.getCustomerId()+" could not be saved and something went wrong",e);			
	}
	return customerDe;
	}

	public TreatmentPlanDomain saveTreatmentPlan(TreatmentPlanDomain treatmentdetails) {
		TreatmentPlanDomain customerDe = new TreatmentPlanDomain();
		try {
			customerDe = treatmentRepo.save(treatmentdetails);
		}catch(Exception e) {
			logger.error("Treatment details for cutomer id "+treatmentdetails.getCustomerId()+" could not be saved and something went wrong",e);			
		}
		return customerDe;
	}
	
	public TreatmentPlanDetailDomain saveTreatmentPlanDetails(TreatmentPlanDetailDomain  treatmentdetails) {
		TreatmentPlanDetailDomain customerDe = new TreatmentPlanDetailDomain();
		try {
			customerDe = treatmentPlanDetailRepo.save(treatmentdetails);
		}catch(Exception e) {
			logger.error("Treatment Plan details for Treatment id "+treatmentdetails.getTreatmentPlanId()+" could not be saved and something went wrong",e);			
		}
		return customerDe;
		}
	
	public TreatmentPlanDomain findTreatmentPlanById(Long id) {
		Optional<TreatmentPlanDomain> treatmentDomain = Optional.of(new  TreatmentPlanDomain());
		try {
			treatmentDomain = treatmentRepo.findById(id);
		}
		catch(Exception e) {
			logger.error("Treatment Plan details for Treatment id "+id+" could not be saved and something went wrong",e);			
				
		}
		return treatmentDomain.orElseGet(null);
	}
	
	public List<CustomerDetail> findCustomerByNameOrPhone(String search){
		Optional<List<CustomerDetail>> customerList = Optional.of(new  ArrayList<CustomerDetail>());
		Long number = null;
		try {
			number = Long.parseLong(search);
		}catch(NumberFormatException nu) {			
			logger.error("Search String is a Name");
			number= 0L;
		}
		try {
			
			customerList = custRepo.findByFirstNameContaining(search);
		}
		catch(Exception e) {
			logger.error("Could Not Find any customer with search param "+search,e);			
				
		}
		return customerList.orElseGet(null);
	}

	public DoctorConsultationDomain findDoctorConsultationDetailsById(Long id) {
		Optional<DoctorConsultationDomain> customerDe =Optional.of( new DoctorConsultationDomain());
		try {
			customerDe = docRepo.findById(id);
		}catch(Exception e) {
			logger.error("Doctor details for cutomer id "+id+" could not be saved and something went wrong",e);			
		}
		return customerDe.orElse(null);
		
	}

	public TreatmentTypeMasterDomain findTreatmentTypeMasterById(Long typeOfTreatement) {
		Optional<TreatmentTypeMasterDomain> customerDe =Optional.of( new TreatmentTypeMasterDomain());
		try {
			customerDe = treatmentTypeMasterRepo.findById(typeOfTreatement);
		}catch(Exception e) {
			logger.error("Doctor details for cutomer id "+typeOfTreatement+" could not be saved and something went wrong",e);			
		}
		return customerDe.orElse(null);
	}

	public InvoiceTypeMaster findInvoiceTypeMasterById(Long invoiceTypeId) {
		Optional<InvoiceTypeMaster> customerDe =Optional.of( new InvoiceTypeMaster());
		try {
			customerDe = invoiceTypeMaster.findById(invoiceTypeId);
		}catch(Exception e) {
			logger.error("Doctor details for cutomer id "+invoiceTypeId+" could not be saved and something went wrong",e);			
		}
		return customerDe.orElse(null);
	}

	public CtAngioDetailsDomain findCtAngioDetailsById(Long id) {
		Optional<CtAngioDetailsDomain> customerDe =Optional.of( new CtAngioDetailsDomain());
		try {
			customerDe = ctAngioRepo.findById(id);
			ctAngioRepo.refresh(customerDe.get());
		}catch(Exception e) {
			logger.error("Doctor details for cutomer id "+id+" could not be saved and something went wrong",e);			
		}
		return customerDe.orElse(null);
	}
}
