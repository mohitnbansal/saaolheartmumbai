package com.saaolheart.mumbai.customer;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;
import javax.swing.text.DefaultEditorKit.CutAction;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.saaolheart.mumbai.common.response.ActionResponse;
import com.saaolheart.mumbai.common.response.ActionStatus;
import com.saaolheart.mumbai.common.utility.CommonUtilities;
import com.saaolheart.mumbai.common.utility.PdfGenerator;
import com.saaolheart.mumbai.invoice.InvoiceDomain;
import com.saaolheart.mumbai.invoice.InvoiceRecieptDetailDomain;
import com.saaolheart.mumbai.invoice.InvoiceStatuses;
import com.saaolheart.mumbai.treatment.ctangiography.CtAngioDetailsDomain;
import com.saaolheart.mumbai.treatment.doctorconsultation.DoctorConsultationDomain;
import com.saaolheart.mumbai.treatment.treatmentplan.TreatmentPlanDetailDomain;
import com.saaolheart.mumbai.treatment.treatmentplan.TreatmentPlanDomain;
import com.saaolheart.mumbai.treatment.treatmentplan.TreatmentStatusConstants;

import fr.opensagres.xdocreport.core.XDocReportException;
import fr.opensagres.xdocreport.template.TemplateEngineKind;

@RestController
@RequestMapping(value="/customer")
@EnableAutoConfiguration
public class CustomerController {
	
	Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private Environment env;
	
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
	@PostMapping(value="/addcustomer")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ActionResponse<CustomerDetail>> saveCustomer(/* @Valid */ @RequestBody CustomerDetail customer,
			HttpServletRequest request,Principal user,
			BindingResult result,HttpServletResponse response){
			
		ActionResponse<CustomerDetail> actionResponse = new ActionResponse<CustomerDetail>();
	
		List<CustomerDetail> custDb = null;
		Set<String> newErrors = new HashSet<String>();
		CustomerDetail customerDb = null;
		/**
		 * User Validation from DB isUserExist
		 */
		if(customer!=null && customer.getId()==null) {
		 custDb = customerService.findCustomerByPhoneNo(customer.getMobileNo());
		 if(custDb!=null && !custDb.isEmpty()) {
			 newErrors.add("User Already Exist in Database");
			 actionResponse.setDocument(customer);
			 return new ResponseEntity<ActionResponse<CustomerDetail>>(actionResponse,HttpStatus.BAD_REQUEST);
		 }
		
		customer.setGeneretedBy(user.getName());
		customer = customerService.saveCustomer(customer);
		if(customer!=null && customer.getId()!=null) {
			String requestRefernceNumber = CommonUtilities.getRequestReference(customer.getId(), customer.getDateOfCreation());
			customer.setCustomerRefId(requestRefernceNumber);
			customer = customerService.saveCustomer(customer);
			actionResponse.setDocument(customer);
			actionResponse.setActionResponse(ActionStatus.SUCCESS);
			 newErrors.add("User Created Successfully in Database");
			 
		}
		}else if(customer!=null && customer.getId() !=null) {
			customerDb = customerService.findCustomerDetailById(customer.getId());
			customerDb.setFirstName(customer.getFirstName());
			customerDb.setMiddleName(customer.getMiddleName());
			customerDb.setLastName(customer.getLastName());
			customerDb.setAge(customer.getAge());
			customerDb.setMobileNo(customer.getMobileNo());
			customerDb.setAadharNumber(customer.getAadharNumber());
			customerDb.setAddress(customer.getAddress());
			customerDb.setAltMobileNo(customer.getAltMobileNo());
			customerDb.setDob(customer.getDob());
			customerDb.setEmailId(customer.getEmailId());
			customerDb.setVistingFor(customer.getVistingFor());
			customerDb.setPanNumber(customer.getPanNumber());
			customerDb.setOccupation(customer.getOccupation());
			customerDb.setMartialStatus(customer.getMartialStatus());
			customerDb = customerService.saveCustomer(customerDb);
			actionResponse.setDocument(customerDb);
			actionResponse.setActionResponse(ActionStatus.SUCCESS);
			 newErrors.add("User Updated Successfully in Database");
		}
		actionResponse.setError(newErrors);
		return new ResponseEntity<ActionResponse<CustomerDetail>>(actionResponse,HttpStatus.OK);
		
	}
	

	@GetMapping(value="/getAllCustomersList")
	public ResponseEntity<List<CustomerDetail>> getDetail(HttpServletRequest request,Principal user,
			HttpServletResponse response) {
		return new ResponseEntity<List<CustomerDetail>>(customerService.findAllCustomerWithSort(),HttpStatus.OK);
	}
	
	
	@GetMapping(value="/detail/{id}")
	public ResponseEntity<CustomerDetail> getCustomerDetailsById(@PathVariable("id") Long id,HttpServletRequest request,Principal user,
			HttpServletResponse response){
		
		CustomerDetail cust = new CustomerDetail();
		cust = customerService.findCustomerDetailById(id);
		if(cust != null && cust.getId()!=null) {
			return new ResponseEntity<CustomerDetail>(cust,HttpStatus.OK);
		}else {
			return new ResponseEntity<CustomerDetail>(cust,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
	
	@PostMapping(value="/savedoctordetails")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ActionResponse<DoctorConsultationDomain>> saveDoctorDetails(/* @Valid */ 
			@RequestBody DoctorConsultationDomain doctordetails,
			HttpServletRequest request,Principal user,
			BindingResult result,HttpServletResponse response)
	{
		
		ActionResponse<DoctorConsultationDomain> actionResponse = new ActionResponse<DoctorConsultationDomain>();
		Set<String> errAndMsg = new HashSet<String>();
		DoctorConsultationDomain doctorDetailsDB = null;
		/**
		 * Add New Record
		 */
		
		if (doctordetails!=null && doctordetails.getId()==null && doctordetails.getCustomerId()!=null) {
			Long invoiceTypeId = doctordetails.getInvoiceMasterTypeId() != null ?  doctordetails.getInvoiceMasterTypeId() : 0L;
			//Setting initial Invoice details.
			InvoiceDomain invoiceDetail = new InvoiceDomain();
			invoiceDetail.setBalanceAmt(doctordetails.getInvoiceTotalamt());
			invoiceDetail.setTotalInvoiceAmt(doctordetails.getInvoiceTotalamt());
			invoiceDetail.setInvoiceStatus(InvoiceStatuses.NOTPAID.getInvoiceStatuses());
			invoiceDetail.setCreatedDate(new Date());
			invoiceDetail.setCustomerId(doctordetails.getCustomerId());
			invoiceDetail.setInvoiceTypeId(invoiceTypeId);//need mapped in UI dynamically
			invoiceDetail.setGeneretedByName(user.getName());
			doctordetails.setInvoiceDomain(invoiceDetail);
			
			doctorDetailsDB = customerService.saveDoctorDetails(doctordetails);
			if(doctorDetailsDB !=null && doctorDetailsDB.getId()!=null) {
				
				actionResponse.setDocument(doctorDetailsDB);
				actionResponse.setActionResponse(ActionStatus.SUCCESS);
				errAndMsg.add("Doctor Details Successfully Updated in DB");	
				 actionResponse.setError(errAndMsg);
			return new ResponseEntity<ActionResponse<DoctorConsultationDomain>>(actionResponse,HttpStatus.OK);
				}
			else {
				errAndMsg.add( "Doctor details not able to Save");
				 actionResponse.setDocument(doctordetails);
				 actionResponse.setError(errAndMsg);
				 
				 return new ResponseEntity<ActionResponse<DoctorConsultationDomain>>(actionResponse,HttpStatus.BAD_REQUEST);
			   }
		}
		/**
		 * Send Error 
		 */
		else  {
			errAndMsg.add( "Doctor details Data missing");
			 actionResponse.setDocument(doctordetails);
			 actionResponse.setError(errAndMsg);
			 return new ResponseEntity<ActionResponse<DoctorConsultationDomain>>(actionResponse,HttpStatus.BAD_REQUEST);

			
		}
	}
	
	
	
	@PostMapping(value="/generatereciept")
	public ResponseEntity<ActionResponse<InvoiceDomain>>  generateRecipt(/* @Valid */ 
			@RequestBody InvoiceDomain invoiceDomain,
			HttpServletRequest request,Principal user,
			BindingResult result,HttpServletResponse response) {
		InvoiceDomain newFromDb = invoiceDomain;
		InvoiceDomain invoiceDomainFromDb = customerService.findInvoiceDomainById(invoiceDomain.getId());
		ActionResponse<InvoiceDomain> actionResponse = new ActionResponse<InvoiceDomain>();
	Set<String> errAndMsg = new HashSet<String>();
		if(invoiceDomainFromDb!=null)
		{
			InvoiceRecieptDetailDomain invRcpt = new InvoiceRecieptDetailDomain();
			invRcpt.setInvoiceId(invoiceDomainFromDb.getId());
			invRcpt.setPaymentDate(new Date());
			invRcpt.setPaymentMode(invoiceDomain.getPaymentMode());
			invRcpt.setPaymentReferenceNo(invoiceDomain.getReferenceNumber());
			invRcpt.setPaymentAmount(invoiceDomain.getPaymentAmount());
			invRcpt.setRecievedBy(invoiceDomain.getGeneretedByName());
			invoiceDomainFromDb.setBalanceAmt(invoiceDomain.getBalanceAmt());
			
			/**
			 * BUG Status is not getting 
			 */
			boolean doAdd = true;
			boolean noCancelled =invoiceDomain!= null && invoiceDomain.getCancelInvoice()!=null && invoiceDomain.getCancelInvoice().equalsIgnoreCase(InvoiceStatuses.CANCELLED.getInvoiceStatuses());
			if((invoiceDomainFromDb.getTotalInvoiceAmt().compareTo(invoiceDomain.getBalanceAmt()) == 0 )
					&& !noCancelled) {
				invoiceDomainFromDb.setInvoiceStatus(InvoiceStatuses.NOTPAID.getInvoiceStatuses());
				
			}else if (invoiceDomain.getBalanceAmt() > 0D && !noCancelled){
				
				invoiceDomainFromDb.setInvoiceStatus(InvoiceStatuses.PARTIALLYPAID.getInvoiceStatuses());
				
			}else if(noCancelled){	
				/**
				 * LOGIC if the Invoice get cancelled	
				 */
				invoiceDomainFromDb.setInvoiceStatus(InvoiceStatuses.CANCELLED.getInvoiceStatuses());
				doAdd = false;
				
			}else if(invoiceDomain.getBalanceAmt() == 0D && !noCancelled){
				invoiceDomainFromDb.setInvoiceStatus(InvoiceStatuses.PAYMENTDONE.getInvoiceStatuses());
			}
			if(doAdd) {
				invoiceDomainFromDb.getInvoiceReciptList().add(invRcpt);
				errAndMsg.add("Reciept Generated Succesfully !");
			}else {
				errAndMsg.add("Invoice Cancelled Succesfully !");
			}
			newFromDb = customerService.saveinvoiceDomain(invoiceDomainFromDb);
		}
		
		
		actionResponse.setDocument(newFromDb);	
		actionResponse.setError(errAndMsg);
		return new ResponseEntity<ActionResponse<InvoiceDomain>>(actionResponse,HttpStatus.OK);

		
	}
	
	@PostMapping(value="/printreciept")
	public ResponseEntity<ByteArrayResource> printReciept(/* @Valid */ 
			@RequestBody InvoiceRecieptDetailDomain invoiceReciptDomain,
			HttpServletRequest request,Principal user,HttpServletResponse response) throws SerialException, SQLException, IOException {
		InvoiceDomain invoiceDomainFromDb = customerService.findInvoiceDomainById(invoiceReciptDomain.getInvoiceId());
	CustomerDetail cust = customerService.findCustomerDetailById(invoiceDomainFromDb.getCustomerId());
	TreatmentPlanDomain treatmentPlan = null;
	for(TreatmentPlanDomain treat:cust.getTreatmentPlanList()) {
		if(invoiceDomainFromDb.getId().equals(treat.getInvoiceDomain().getId())){
			treatmentPlan = treat;
			invoiceDomainFromDb.setTreatmentPlan(treatmentPlan);
			break;
		}
	}
	invoiceDomainFromDb.setCustomerName(cust.getFirstName() + " " + cust.getLastName());
		byte[] mergedOutput = null;
		  Map<String, Object> nonImageVariableMap = new HashMap<String, Object>();
		  nonImageVariableMap.put("invoiceReciptDomain", invoiceReciptDomain);
		  nonImageVariableMap.put("invoiceDomainFromDb", invoiceDomainFromDb);
		  nonImageVariableMap.put("installment", invoiceDomainFromDb.getInvoiceReciptList().size());

		 String templatePath = 							env.getProperty("spring.excel.recipt.path");
				/**
				 * Below is the Code for Image Setting;
				 */
//				  Map<String, String>  imageVariablesWithPathMap = new HashMap<String, String>();
//				  imageVariablesWithPathMap
//				  .put("header_image_logo", "/home/mohit/ProtechnicWorkspace/SaaolHearts/Project/Root/saaolheartmumbai/im.png");
				  try {
					mergedOutput =	  PdfGenerator
							.mergeAndGeneratePDFOutput
							(templatePath,
							TemplateEngineKind.Velocity,
							nonImageVariableMap,
							null);
				} catch (IOException | XDocReportException | Docx4JException  e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (Exception  e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  
				  FileOutputStream os = new FileOutputStream(templatePath+invoiceReciptDomain.getId()+".pdf");
				  os.write(mergedOutput);
				  os.flush();
				  os.close();
				  ByteArrayResource resource = new ByteArrayResource(mergedOutput);
				  response.setContentType("text/pdf; charset=utf-8");
					response.setHeader("Content-Disposition", "attachment; filename=" + invoiceReciptDomain.getId()+".pdf");
					response.setHeader("filename", invoiceReciptDomain.getId()+".pdf");
			return	new ResponseEntity<ByteArrayResource>(resource,HttpStatus.OK);

	}
	
	
	@GetMapping(value="/printmou")
	public ResponseEntity<ByteArrayResource> printMou(@RequestParam("ctConsultationId") Long id,
			HttpServletRequest request,Principal user,HttpServletResponse response) throws IOException{		
		DoctorConsultationDomain doctorCons = customerService.findDoctorConsultationDetailsById(id);
			CustomerDetail cust = customerService.findCustomerDetailById(doctorCons.getCustomerId());
			
			doctorCons.setCustomerName(cust.getFirstName() + " " + cust.getLastName());
				byte[] mergedOutput = null;
				  Map<String, Object> nonImageVariableMap = new HashMap<String, Object>();
				  nonImageVariableMap.put("doctorCons", doctorCons);
				  nonImageVariableMap.put("cust", cust);

				 String templatePath = 	env.getProperty("spring.excel.mou.path");
						/**
						 * Below is the Code for Image Setting;
						 */
//						  Map<String, String>  imageVariablesWithPathMap = new HashMap<String, String>();
//						  imageVariablesWithPathMap
//						  .put("header_image_logo", "/home/mohit/ProtechnicWorkspace/SaaolHearts/Project/Root/saaolheartmumbai/im.png");
						  try {
							mergedOutput =	  PdfGenerator
									.mergeAndGeneratePDFOutput
									(templatePath,
									TemplateEngineKind.Velocity,
									nonImageVariableMap,
									null);
						} catch (IOException | XDocReportException | Docx4JException  e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}catch (Exception  e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						  
						  FileOutputStream os = new FileOutputStream(templatePath+doctorCons.getId()+".pdf");
						  os.write(mergedOutput);
						  os.flush();
						  os.close();
						  ByteArrayResource resource = new ByteArrayResource(mergedOutput);
						  response.setContentType("text/pdf; charset=utf-8");
							response.setHeader("Content-Disposition", "attachment; filename=" + doctorCons.getId()+".pdf");
							response.setHeader("filename", doctorCons.getId()+".pdf");
					return	new ResponseEntity<ByteArrayResource>(resource,HttpStatus.OK);
}
	/**
	 * CT ANGIO MEthods
	 */
	
	@PostMapping(value="/savectangiodetails")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ActionResponse<CtAngioDetailsDomain>> saveCtAngioDetails(/* @Valid */ 
			@RequestBody CtAngioDetailsDomain ctAngioDetails,
			HttpServletRequest request,Principal user,
			BindingResult result,HttpServletResponse response)
	{
		
		ActionResponse<CtAngioDetailsDomain> actionResponse = new ActionResponse<CtAngioDetailsDomain>();
		Set<String> errAndMsg = new HashSet<String>();
		CtAngioDetailsDomain ctAngioFromDb = null;
		/**
		 * Add New Record
		 */
		
		if (ctAngioDetails!=null && ctAngioDetails.getId()==null && ctAngioDetails.getCustomerId()!=null) {
			Long invoiceTypeId = ctAngioDetails.getInvoiceMasterTypeId() != null ?  ctAngioDetails.getInvoiceMasterTypeId() : 0L;
			//Setting initial Invoice details.
			InvoiceDomain invoiceDetail = new InvoiceDomain();
			invoiceDetail.setBalanceAmt(ctAngioDetails.getInvoiceTotalamt());
			invoiceDetail.setTotalInvoiceAmt(ctAngioDetails.getInvoiceTotalamt());
			invoiceDetail.setCreatedDate(new Date());
			invoiceDetail.setCustomerId(ctAngioDetails.getCustomerId());
			invoiceDetail.setInvoiceStatus(InvoiceStatuses.NOTPAID.getInvoiceStatuses());
			invoiceDetail.setInvoiceTypeId(invoiceTypeId);//need mapped in UI dynamically
			invoiceDetail.setGeneretedByName(user.getName());
			ctAngioDetails.setInvoiceDomain(invoiceDetail);
			
			ctAngioFromDb = customerService.saveCtAngioDetails(ctAngioDetails);
			if(ctAngioFromDb !=null && ctAngioFromDb.getId()!=null) {
				actionResponse.setDocument(ctAngioFromDb);
				actionResponse.setActionResponse(ActionStatus.SUCCESS);
				errAndMsg.add( "CT Angio Details Successfully Updated in DB");	
				 actionResponse.setError(errAndMsg);
			return new ResponseEntity<ActionResponse<CtAngioDetailsDomain>>(actionResponse,HttpStatus.OK);
				}
			else {
				errAndMsg.add("CT Angio details not able to Save");
				 actionResponse.setDocument(ctAngioDetails);
				 actionResponse.setError(errAndMsg);
				 return new ResponseEntity<ActionResponse<CtAngioDetailsDomain>>(actionResponse,HttpStatus.BAD_REQUEST);
			   }
		}
		/**
		 * Send Error 
		 */
		else  {
			errAndMsg.add( "CT Angio details Data missing");
			 actionResponse.setDocument(ctAngioDetails);
			 actionResponse.setError(errAndMsg);
			 return new ResponseEntity<ActionResponse<CtAngioDetailsDomain>>(actionResponse,HttpStatus.BAD_REQUEST);

			
		}
	}
	
	
	/**
	 * Treatment MEthods
	 */
	@PostMapping(value="/savetreatmentdetails")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ActionResponse<TreatmentPlanDomain>> saveTreatmentDetails(/* @Valid */ 
			@RequestBody TreatmentPlanDomain treatmentdetails,
			HttpServletRequest request,Principal user,
			BindingResult result,HttpServletResponse response)
	{
		
		ActionResponse<TreatmentPlanDomain> actionResponse = new ActionResponse<TreatmentPlanDomain>();
		Set<String> mMap = new HashSet<>();
		TreatmentPlanDomain treatmentFromDB = null;
		/**
		 * Add New Record
		 */
		
		if (treatmentdetails!=null && treatmentdetails.getId()==null && treatmentdetails.getCustomerId()!=null) {
			Long invoiceTypeId = treatmentdetails.getInvoiceMasterTypeId() != null ?  treatmentdetails.getInvoiceMasterTypeId() : 0L;
			//Setting initial Invoice details.
			InvoiceDomain invoiceDetail = new InvoiceDomain();
			invoiceDetail.setBalanceAmt(treatmentdetails.getInvoiceTotalamt());
			invoiceDetail.setTotalInvoiceAmt(treatmentdetails.getInvoiceTotalamt());
			invoiceDetail.setCustomerId(treatmentdetails.getCustomerId());
			invoiceDetail.setInvoiceStatus(InvoiceStatuses.NOTPAID.getInvoiceStatuses());
			invoiceDetail.setCreatedDate(new Date());
			invoiceDetail.setInvoiceTypeId(invoiceTypeId);//need mapped in UI dynamically
			invoiceDetail.setGeneretedByName(user.getName());
			treatmentdetails.setInvoiceDomain(invoiceDetail);
			treatmentdetails.setTreatmentStatus(TreatmentStatusConstants.PENDING);
			treatmentFromDB = customerService.saveTreatmentPlan(treatmentdetails);
			if(treatmentFromDB !=null && treatmentFromDB.getId()!=null) {
				actionResponse.setDocument(treatmentFromDB);
				actionResponse.setActionResponse(ActionStatus.SUCCESS);
				mMap.add( "Treatment Details Successfully Updated in DB");		
				actionResponse.setError(mMap);
			return new ResponseEntity<ActionResponse<TreatmentPlanDomain>>(actionResponse,HttpStatus.OK);
				}
			else {
				 mMap.add("Treatment Details  not able to Save");
				 actionResponse.setDocument(treatmentdetails);
				 actionResponse.setError(mMap);
				 return new ResponseEntity<ActionResponse<TreatmentPlanDomain>>(actionResponse,HttpStatus.BAD_REQUEST);
			   }
		}
		/**
		 * Send Error 
		 */
		else  {
			 mMap.add( "Treatment Details Data missing");
			 actionResponse.setDocument(treatmentdetails);
			 actionResponse.setError(mMap);
			 return new ResponseEntity<ActionResponse<TreatmentPlanDomain>>(actionResponse,HttpStatus.BAD_REQUEST);

			
		}
	}
	

	@PostMapping(value="/savetreatmentplandetails")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ActionResponse<TreatmentPlanDomain>> saveTreatmentPlanDetails(/* @Valid */ 
			@RequestBody TreatmentPlanDetailDomain treatmentPlanDetails,
			HttpServletRequest request,Principal user,
			BindingResult result,HttpServletResponse response)
	{
	
		ActionResponse<TreatmentPlanDomain> actionResponse = new ActionResponse<TreatmentPlanDomain>();
		MultiValueMap<String, String> mMap = new LinkedMultiValueMap<>();
		TreatmentPlanDetailDomain treatmentPlanDetailsFromDB = null;
		TreatmentPlanDomain treatmentPlanDb = null;
		/**
		 * Add New Record
		 */
		
		if(treatmentPlanDetails!=null && treatmentPlanDetails.getTreatmentPlanId() != null) {
			treatmentPlanDb = customerService.findTreatmentPlanById(treatmentPlanDetails.getTreatmentPlanId());
			if(treatmentPlanDb.getTreatmentPlanDetailsList() != null) {
				treatmentPlanDb.getTreatmentPlanDetailsList().add(treatmentPlanDetails);
			}else {
				List<TreatmentPlanDetailDomain> treatmentPlanList = new ArrayList<TreatmentPlanDetailDomain>();
				treatmentPlanDb.setTreatmentPlanDetailsList(treatmentPlanList);
				treatmentPlanDb.getTreatmentPlanDetailsList().add(treatmentPlanDetails);
			}
			
		}
		treatmentPlanDb  = customerService.saveTreatmentPlan(treatmentPlanDb);
		actionResponse.setDocument(treatmentPlanDb);
		actionResponse.setActionResponse(ActionStatus.SUCCESS);
		mMap.add("success", "Treatment Details Successfully Updated in DB");			
	   return new ResponseEntity<ActionResponse<TreatmentPlanDomain>>(actionResponse,mMap,HttpStatus.OK);
	}
	@GetMapping(value="/getallinvoicesforcustomer")
	public void getAllInvoicesForCustomer() {
		
	}
	
/**
 * 
 * @param searchParam
 * @param request
 * @param user
 * @param response
 * @return
 */
	/*
	 * This methods need to be improved as this method needs to be capable of searching by name and phone number , repo query need to 
	 * be implemented.
	 */
	@GetMapping(value="/getcustomerbysearch")
	public ResponseEntity<List<CustomerDetail>> getCustomerDetailsBySearchParam(@RequestParam("searchParam") String searchParam,
			HttpServletRequest request,Principal user,
			HttpServletResponse response){
	return  new ResponseEntity<>(customerService.findCustomerByNameOrPhone(searchParam),HttpStatus.OK);
	}
	
//	@GetMapping(value="/getcustomeralltreatment/{id}")
//	public ResponseEntity<List<TreatmentPlanDomain>> getAllTreatmentForCustomerById(@PathVariable("id") Long id,
//			HttpServletRequest request,Principal user,
//			HttpServletResponse response){
//	return  new ResponseEntity<List<TreatmentPlanDomain>>(customerService.findAllTreatmentByCustomerId(id),HttpStatus.OK);
//	}
	
	@PostMapping(value="/cancelInvoice")
	public ResponseEntity<ActionResponse<CtAngioDetailsDomain>>  cancelInvoice(/* @Valid */ 
			@RequestBody CtAngioDetailsDomain ctAngio,
			HttpServletRequest request,Principal user,
			BindingResult result,HttpServletResponse response) {
		CtAngioDetailsDomain ctAngioTotalSaved = null;
		InvoiceDomain invoiceDomainFromDb = customerService.findInvoiceDomainById(ctAngio.getInvoiceDomain().getId());
		ActionResponse<CtAngioDetailsDomain> actionResponse = new ActionResponse<CtAngioDetailsDomain>();
		MultiValueMap<String, String> mMap = new LinkedMultiValueMap<>();
		if(invoiceDomainFromDb!=null)
		{
			invoiceDomainFromDb.setInvoiceStatus(InvoiceStatuses.CANCELLED.getInvoiceStatuses());
			if(invoiceDomainFromDb.getInvoiceReciptList()!=null && !invoiceDomainFromDb.getInvoiceReciptList().isEmpty()) {
			for(InvoiceRecieptDetailDomain recpt: invoiceDomainFromDb.getInvoiceReciptList()) {
				recpt.setRefundAmount(recpt.getPaymentAmount());				
			}
		}
		
			ctAngio.setInvoiceTotalamt(ctAngio.getInvoiceDomain().getNewInvoiceAmountInCaseofCancel());
			ctAngio.setRefDate(new Date());
			ctAngio.setInvoiceDomain(invoiceDomainFromDb);
			ctAngio.getInvoiceDomain().setBalanceAmt(0D);
			ctAngio.setId(null);
			CtAngioDetailsDomain ctAngioFromDb = 	
					saveCtAngioDetails(ctAngio, request, user, result, response).getBody().getDocument();	
			ctAngioFromDb.getInvoiceDomain().setPaymentAmount(ctAngio.getInvoiceDomain().getNewInvoiceAmountInCaseofCancel());
			ctAngioFromDb.getInvoiceDomain().setBalanceAmt(0D);
			generateRecipt(ctAngioFromDb.getInvoiceDomain(), request, user, result, response);
			 ctAngioTotalSaved = customerService.findCtAngioDetailsById(ctAngioFromDb.getId());
		}
		actionResponse.setDocument(ctAngioTotalSaved);
		actionResponse.setActionResponse(ActionStatus.SUCCESS);
		mMap.add("success", "Doctor Details Successfully Updated in DB");			
	return new ResponseEntity<ActionResponse<CtAngioDetailsDomain>>(actionResponse,mMap,HttpStatus.OK);
	}


	@PostMapping(value="/updateinvoice")
	public void updateInvoices() {
			
		
	}
}
