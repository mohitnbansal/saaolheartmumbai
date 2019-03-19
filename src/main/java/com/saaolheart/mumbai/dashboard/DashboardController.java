package com.saaolheart.mumbai.dashboard;

import java.security.Principal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.saaolheart.mumbai.common.response.ActionResponse;
import com.saaolheart.mumbai.common.response.ActionStatus;
import com.saaolheart.mumbai.customer.AppointmentStatusesConstants;
import com.saaolheart.mumbai.customer.AppointmentType;
import com.saaolheart.mumbai.customer.CustomerAppointmentDomain;
import com.saaolheart.mumbai.customer.CustomerDetail;
import com.saaolheart.mumbai.customer.CustomerService;
import com.saaolheart.mumbai.invoice.InvoiceDomain;
import com.saaolheart.mumbai.treatment.treatmentplan.TreatmentPlanDetailDomain;
import com.saaolheart.mumbai.treatment.treatmentplan.TreatmentPlanDomain;
import com.saaolheart.mumbai.treatment.treatmentplan.TreatmentStatusConstants;

@Controller
@RequestMapping(value="/dashboard")
public class DashboardController {
	
	

	Logger logger = LoggerFactory.getLogger(DashboardController.class);
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private  DashboardService dashboardService;
	
	
	@PostMapping(value="/addappointment")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ActionResponse<CustomerAppointmentDomain>>  createAppointment(/* @Valid */
			@RequestBody CustomerAppointmentDomain appointment,
			HttpServletRequest request,Principal user,
			BindingResult result,HttpServletResponse response) {
		
		ActionResponse<CustomerAppointmentDomain> actionResponse = new ActionResponse<CustomerAppointmentDomain>();
		MultiValueMap<String, String> mMap = new LinkedMultiValueMap<>();
		CustomerAppointmentDomain customerApointmentDb = null;
		if(appointment!=null && appointment.getCustomerId()!=null) {
			
			
		CustomerDetail customer = customerService.findCustomerDetailById(appointment.getCustomerId());
		if(customer.getCustomerAppointmentList() !=null && !customer.getCustomerAppointmentList().isEmpty()) {
			Collections.sort(customer.getCustomerAppointmentList(),CustomerAppointmentDomain.sortByScheduleNumber);
			int size  = customer.getCustomerAppointmentList().size();
			CustomerAppointmentDomain lastAppointment = customer.getCustomerAppointmentList().get(size-1);
				appointment.setIsVisitDone(AppointmentStatusesConstants.PENDING);
				appointment.setDateOfScheduling(new Date());
				appointment.setScheduledNumber(lastAppointment.getScheduledNumber()+1);
				appointment.setTypeOfAppointment(AppointmentType.getAppointmentType(appointment.getTypeOfAppointmentString()));
				 
			customer.getCustomerAppointmentList().add(appointment);
		}else {
				appointment.setIsVisitDone(AppointmentStatusesConstants.PENDING);
				appointment.setDateOfScheduling(new Date());
				appointment.setScheduledNumber(1);
				appointment.setTypeOfAppointment(AppointmentType.getAppointmentType(appointment.getTypeOfAppointmentString()));
				customer.getCustomerAppointmentList().add(appointment);
		}
		CustomerDetail customerDb = customerService.saveCustomer(customer);
		 int dbSize = customerDb.getCustomerAppointmentList().size();
		 customerApointmentDb = customerDb.getCustomerAppointmentList().get(dbSize-1);
		 actionResponse.setDocument(customerApointmentDb);
			actionResponse.setActionResponse(ActionStatus.SUCCESS);
			mMap.add("success", "User Created Successfully in Database");
		}
 
		return new ResponseEntity<ActionResponse<CustomerAppointmentDomain>>(actionResponse,mMap,HttpStatus.OK);
		
	}
	
	@PostMapping(value="/markappointment")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ActionResponse<CustomerAppointmentDomain>>  markPatientAppointment(/* @Valid */
			@RequestBody CustomerAppointmentDomain appointment,
			HttpServletRequest request,Principal user,
			BindingResult result,HttpServletResponse response)
	{
	

		ActionResponse<CustomerAppointmentDomain> actionResponse = new ActionResponse<CustomerAppointmentDomain>();
		MultiValueMap<String, String> mMap = new LinkedMultiValueMap<>();
		CustomerDetail customerFromDb = customerService.findCustomerDetailById(appointment.getCustomerId());
		if(appointment!=null && appointment.getId()!=null) {
			/*
			 * Add methods such that Treatment Plan also will be updated
			 */
			switch(appointment.getTypeOfAppointmentString()) {
			/*
			 * case "In House": { break;
			 * 
			 * } case "Dr Appointment": {
			 * 
			 * break;
			 * 
			 * }
			 */
			case "Treatment ECP":
			{
				
				boolean isModified = false;
				
				for(TreatmentPlanDomain treatment:customerFromDb.getTreatmentPlanList()) {					
					if(treatment.getTreatmentStatus().equalsIgnoreCase(TreatmentStatusConstants.PENDING)
							&& treatment.getTreatmentMaster().getTreatmentName().equalsIgnoreCase("ECP")) {
						Duration newDuration = treatment.getTime().plus(appointment.getDurationOfTreatment()) ;
						treatment.setTime(newDuration);
						TreatmentPlanDetailDomain newTreatment = new  TreatmentPlanDetailDomain();
						newTreatment.setDuration(appointment.getDurationOfTreatment());
						newTreatment.setMachineNo(appointment.getMachineNo());
						newTreatment.setTreatmentDate(new Date());
						newTreatment.setTreatmentPlanId(treatment.getId());
						newTreatment.setComplaints(appointment.getPostScheduleDescription());
						treatment.getTreatmentPlanDetailsList().add(newTreatment);				
						if(treatment.getNoOfSittings()==treatment.getTreatmentMaster().getTotalNoOfSittings()) {
							treatment.setTreatmentStatus(TreatmentStatusConstants.COMPLETED);
						}
						isModified = true;
					}
					
				}
				if(isModified) {
					customerService.saveCustomer(customerFromDb);
				}
				break;
				
			}
			case "Treatment BCP":
			{	
				boolean isModified = false;
				for(TreatmentPlanDomain treatment:customerFromDb.getTreatmentPlanList()) {					
					if(treatment.getTreatmentStatus().equalsIgnoreCase(TreatmentStatusConstants.PENDING)
							&& treatment.getTreatmentMaster().getTreatmentName().equalsIgnoreCase("BCP")) {
				Integer noOfSittings = treatment.getNoOfSittings() +  1;
				treatment.setNoOfSittings(noOfSittings);
				TreatmentPlanDetailDomain newTreatment = new  TreatmentPlanDetailDomain();				
				newTreatment.setTreatmentDate(new Date());
				newTreatment.setTreatmentPlanId(treatment.getId());
				newTreatment.setComplaints(appointment.getPostScheduleDescription());
				treatment.getTreatmentPlanDetailsList().add(newTreatment);
				
				if(treatment.getNoOfSittings()==treatment.getTreatmentMaster().getTotalNoOfSittings()) {
					treatment.setTreatmentStatus(TreatmentStatusConstants.COMPLETED);
				}
				isModified = true;
					}
				}
				
				if(isModified) 
				{
					customerService.saveCustomer(customerFromDb);
				}
				
				break;
				
				}
			}

			CustomerAppointmentDomain appointmentDb = dashboardService.findAppontmentDetailById(appointment.getId());
			Integer maxCount = dashboardService.findLastVisitOfCustomerByCustomerId(appointment.getCustomerId(),
			AppointmentType.getAppointmentType(appointment.getTypeOfAppointmentString()));
			appointmentDb.setVisitNumber(maxCount+1);
			appointmentDb.setIsVisitDone("YES");
			appointmentDb.setVisitDateAndTime(new Date());
			CustomerAppointmentDomain savedObject = dashboardService.saveAppointment(appointmentDb);
			actionResponse.setDocument(savedObject);
			actionResponse.setActionResponse(ActionStatus.SUCCESS);
			mMap.add("success", "User Appointment Succesfull");
			
		}
		
		return new ResponseEntity<ActionResponse<CustomerAppointmentDomain>>(actionResponse,mMap,HttpStatus.OK);
	}
	

	
	@GetMapping(value="getpaymentpending")
	public  ResponseEntity<ActionResponse<List<InvoiceDomain>>>  getPaymentPendingCustomerList(HttpServletRequest request,Principal user,
			BindingResult result,HttpServletResponse response) 
	{
		ActionResponse<List<InvoiceDomain>> actionResponse = new ActionResponse<List<InvoiceDomain>>();
		MultiValueMap<String, String> mMap = new LinkedMultiValueMap<>();
		List<InvoiceDomain> invoiceDetailList = new ArrayList<>();
		invoiceDetailList = dashboardService.findCustomerWithPaymentPending();
		actionResponse.setDocument(invoiceDetailList);
		return new ResponseEntity<ActionResponse<List<InvoiceDomain>>>(actionResponse,mMap,HttpStatus.OK);
	}
	
	
	
	

	@GetMapping(value="getpateintqueuelist")
	public void getPateintsQueueList(HttpServletRequest request,Principal user,
			BindingResult result,HttpServletResponse response) 
	{
		List<CustomerAppointmentDomain> appointmentList = new ArrayList<CustomerAppointmentDomain>();
		appointmentList = dashboardService.getAppointmentPendingList();
		
	}
	
	@GetMapping(value="getnewjoinee")
	public void getNewjoineeListForToday(HttpServletRequest request,Principal user,
			BindingResult result,HttpServletResponse response) 
	{
		List<CustomerDetail> customerDetailList = new ArrayList<>();
		customerDetailList = dashboardService.findTodayJoinedCustomerList();
	}
	
	@GetMapping(value="getinhouseappointments")
	public void getInHouseAppointmentList(HttpServletRequest request,Principal user,
			BindingResult result,HttpServletResponse response) 
	{
		
			
	}
	
	
	@GetMapping(value="gettreatmentpendinglist")
	public void getTreatmentPendingPlanList() {
		
	}
	
	
	@GetMapping(value="getlowstocks")
	public void getLowStockList() {
		
	}
	
}
