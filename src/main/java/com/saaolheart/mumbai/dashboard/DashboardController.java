package com.saaolheart.mumbai.dashboard;

import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.saaolheart.mumbai.common.response.ActionResponse;
import com.saaolheart.mumbai.common.response.ActionStatus;
import com.saaolheart.mumbai.common.utility.Constants;
import com.saaolheart.mumbai.customer.AppointmentConstants;
import com.saaolheart.mumbai.customer.AppointmentType;
import com.saaolheart.mumbai.customer.CustomerAppointmentDomain;
import com.saaolheart.mumbai.customer.CustomerDetail;
import com.saaolheart.mumbai.customer.CustomerService;
import com.saaolheart.mumbai.customer.VisitngFor;
import com.saaolheart.mumbai.invoice.InvoiceDomain;
import com.saaolheart.mumbai.treatment.treatmentplan.TreatmentPlanDetailDomain;
import com.saaolheart.mumbai.treatment.treatmentplan.TreatmentPlanDomain;
import com.saaolheart.mumbai.treatment.treatmentplan.TreatmentStatusConstants;

@Controller
@RequestMapping(value = "/dashboard")
public class DashboardController {

	Logger logger = LoggerFactory.getLogger(DashboardController.class);

	@Autowired
	private CustomerService customerService;

	@Autowired
	private DashboardService dashboardService;
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	    dateFormat.setLenient(false);
	   

	    // true passed to CustomDateEditor constructor means convert empty String to null
	    binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	    binder.registerCustomEditor(Long.class,new CustomNumberEditor(Long.class, true));
	    binder.registerCustomEditor(Float.class,new CustomNumberEditor(Float.class, true));
	}
	@PostMapping(value = "/scheduleappointment")
	// @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ActionResponse<CustomerAppointmentDomain>> createAppointment(/* @Valid */
			@RequestBody CustomerAppointmentDomain appointment, HttpServletRequest request, Principal user,
			BindingResult result, HttpServletResponse response) throws ParseException {
		
		ActionResponse<CustomerAppointmentDomain> actionResponse = new ActionResponse<CustomerAppointmentDomain>();
		MultiValueMap<String, String> mMap = new LinkedMultiValueMap<>();
		
		
		Date newDate = parse(appointment.getExpectedTime());

		appointment.setDateOfScheduling(newDate);
		appointment.setExpectedTime(newDate);
		// appointment.setDateOfScheduling(dateFormat.format(appointment.getDateOfScheduling());
		CustomerAppointmentDomain customerApointmentDb = null;
		if (appointment != null && appointment.getCustomerId() != null) {
			CustomerDetail customer = customerService.findCustomerDetailById(appointment.getCustomerId());
			TreatmentPlanDetailDomain treatmentDetalPlanSave = null;

			if (appointment != null) {
				/*
				 * Add methods such that Treatment Plan also will be updated
				 */
				switch (appointment.getTypeOfAppointmentString()) {
				/*
				 * case "In House": { break;
				 * 
				 * } case "Dr Appointment": {
				 * 
				 * break;
				 * 
				 * }
				 */
				case "Treatment ECP": {
					Integer noMachine  =appointment.getMachineNo();
					Integer dateNo = null;
					 try {
						  LocalDateTime local =
						  LocalDateTime.ofInstant(appointment.getExpectedTime().toInstant()
						  , ZoneId.systemDefault()); 
						  dateNo = local.getHour();
						  
						  }catch(DateTimeException e) {
						  
						  } 
				boolean isTrue = 	assignAndValidateAppointment(noMachine, dateNo,appointment.getExpectedTime());
				if(isTrue) {
					actionResponse.setActionResponse(ActionStatus.FAILED);
					mMap.add("failed", "Another Pateint is already scheduled for the same slot please select other");
					return new ResponseEntity<ActionResponse<CustomerAppointmentDomain>>(actionResponse, mMap, HttpStatus.CONFLICT);
				}else {
					
				
					for (TreatmentPlanDomain treatment : customer.getTreatmentPlanList()) {
						if (treatment.getTreatmentStatus().equalsIgnoreCase(TreatmentStatusConstants.PENDING)
								&& treatment.getTreatmentMaster().getTreatmentName().equalsIgnoreCase("ECP")) {

							TreatmentPlanDetailDomain newTreatment = new TreatmentPlanDetailDomain();
							newTreatment.setTreatmentPlanId(treatment.getId());
							newTreatment.setIsTreatmentDone(Constants.PENDING);
							newTreatment.setTreatmentScheduledDate(appointment.getExpectedTime());
							newTreatment.setMachineNo(appointment.getMachineNo());
							// treatment.getTreatmentPlanDetailsList().add(newTreatment);
							treatmentDetalPlanSave = newTreatment;

						}
					}
				}

					

					break;

				}
				case "Treatment BCA": {

					for (TreatmentPlanDomain treatment : customer.getTreatmentPlanList()) {
						if (treatment.getTreatmentStatus().equalsIgnoreCase(TreatmentStatusConstants.PENDING)
								&& treatment.getTreatmentMaster().getTreatmentName().equalsIgnoreCase("BCA")) {

							TreatmentPlanDetailDomain newTreatment = new TreatmentPlanDetailDomain();
							newTreatment.setIsTreatmentDone(Constants.PENDING);
							newTreatment.setTreatmentPlanId(treatment.getId());
							newTreatment.setTreatmentScheduledDate(appointment.getDateOfScheduling());
							// treatment.getTreatmentPlanDetailsList().add(newTreatment);
							treatmentDetalPlanSave = newTreatment;
						}
					}

					break;

				}
				}

				TreatmentPlanDetailDomain savedTreatmentPlan = dashboardService
						.saveTreatmentDetailPlan(treatmentDetalPlanSave);
				CustomerDetail customerNewFromDb = customerService.findCustomerDetailById(appointment.getCustomerId());
				if (customerNewFromDb.getCustomerAppointmentList() != null
						&& !customer.getCustomerAppointmentList().isEmpty()) {
					Collections.sort(customerNewFromDb.getCustomerAppointmentList(),
							CustomerAppointmentDomain.sortByScheduleNumber);
					int size = customerNewFromDb.getCustomerAppointmentList().size();
					CustomerAppointmentDomain lastAppointment = customer.getCustomerAppointmentList().get(size - 1);
					appointment.setIsVisitDone(AppointmentConstants.PENDING);
					appointment.setDateOfScheduling(new Date());
					appointment.setExpectedTime(appointment.getDateOfScheduling());
					appointment.setScheduledNumber(lastAppointment.getScheduledNumber() + 1);
					appointment.setTypeOfAppointment(
							AppointmentType.getAppointmentType(appointment.getTypeOfAppointmentString()));
					appointment.setTreatmentDetailPlanId(savedTreatmentPlan.getId());
					appointment.setTreatmentPlanId(savedTreatmentPlan.getTreatmentPlanId());
					customerNewFromDb.getCustomerAppointmentList().add(appointment);
				} else {
					appointment.setIsVisitDone(AppointmentConstants.PENDING);
					appointment.setDateOfScheduling(new Date());
					appointment.setExpectedTime(appointment.getDateOfScheduling());
					appointment.setScheduledNumber(1);
					appointment.setTypeOfAppointment(
							AppointmentType.getAppointmentType(appointment.getTypeOfAppointmentString()));
					appointment.setTreatmentDetailPlanId(savedTreatmentPlan.getId());
					appointment.setTreatmentPlanId(savedTreatmentPlan.getTreatmentPlanId());
					customerNewFromDb.getCustomerAppointmentList().add(appointment);
				}
				CustomerDetail customerDb = customerService.saveCustomer(customerNewFromDb);
				int dbSize = customerDb.getCustomerAppointmentList().size();
				customerApointmentDb = customerDb.getCustomerAppointmentList().get(dbSize - 1);
				actionResponse.setDocument(customerApointmentDb);
				actionResponse.setActionResponse(ActionStatus.SUCCESS);
				mMap.add("success", "User Created Successfully in Database");
			}
		}
		return new ResponseEntity<ActionResponse<CustomerAppointmentDomain>>(actionResponse, mMap, HttpStatus.OK);

	}

	@PostMapping(value = "/markappointment")
	// @PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ActionResponse<CustomerAppointmentDomain>> markPatientAppointment(/* @Valid */
			@RequestBody CustomerAppointmentDomain appointment, HttpServletRequest request, Principal user,
			BindingResult result, HttpServletResponse response) {

		ActionResponse<CustomerAppointmentDomain> actionResponse = new ActionResponse<CustomerAppointmentDomain>();
		MultiValueMap<String, String> mMap = new LinkedMultiValueMap<>();
		boolean isTreatmentModified = false;
		TreatmentPlanDomain treatment = null;
		if (appointment != null && appointment.getTreatmentPlanId() != null) {
			treatment = dashboardService.findTreatmentPlanById(appointment.getTreatmentPlanId());
		}

		if (appointment != null && appointment.getId() != null) {
			/*
			 * Add methods such that Treatment Plan also will be updated
			 */
			switch (appointment.getTypeOfAppointmentString()) {
			/*
			 * case "In House": { break;
			 * 
			 * } case "Dr Appointment": {
			 * 
			 * break;
			 * 
			 * }
			 */
			case "Treatment ECP": {

				if (treatment.getTreatmentStatus().equalsIgnoreCase(TreatmentStatusConstants.PENDING)
						&& treatment.getTreatmentMaster().getTreatmentName().equalsIgnoreCase("ECP")) {
					Duration newDuration = treatment.getTime().plus(appointment.getDurationOfTreatment());
					treatment.setTime(newDuration);

					for (TreatmentPlanDetailDomain newTreatment : treatment.getTreatmentPlanDetailsList()) {
						if (newTreatment.getId() == appointment.getTreatmentDetailPlanId()) {
							newTreatment.setDuration(appointment.getDurationOfTreatment());
							newTreatment.setMachineNo(appointment.getMachineNo());
							newTreatment.setTreatmentDate(new Date());
							newTreatment.setTreatmentPlanId(treatment.getId());
							newTreatment.setIsTreatmentDone(Constants.COMPLETED);
							newTreatment.setComplaints(appointment.getPostScheduleDescription());
							isTreatmentModified = true;
						}
					}

					if (treatment.getNoOfSittings() == treatment.getTreatmentMaster().getTotalNoOfSittings()) {
						treatment.setTreatmentStatus(TreatmentStatusConstants.COMPLETED);
					}

				}

				break;

			}
			case "Treatment BCA": {
				if (treatment.getTreatmentStatus().equalsIgnoreCase(TreatmentStatusConstants.PENDING)
						&& treatment.getTreatmentMaster().getTreatmentName().equalsIgnoreCase("BCA")) {
					Integer noOfSittings = treatment.getNoOfSittings() + 1;
					treatment.setNoOfSittings(noOfSittings);
					for (TreatmentPlanDetailDomain newTreatment : treatment.getTreatmentPlanDetailsList()) {
						if (newTreatment.getId() == appointment.getTreatmentDetailPlanId()) {
							newTreatment.setTreatmentDate(new Date());
							newTreatment.setTreatmentPlanId(treatment.getId());
							newTreatment.setComplaints(appointment.getPostScheduleDescription());
							newTreatment.setIsTreatmentDone(Constants.COMPLETED);
							isTreatmentModified = true;
						}
					}
					if (treatment.getNoOfSittings() == treatment.getTreatmentMaster().getTotalNoOfSittings()) {
						treatment.setTreatmentStatus(TreatmentStatusConstants.COMPLETED);
					}

				}
			}

				break;

			}
		}

		if (isTreatmentModified) {
			dashboardService.saveTreatmentPlan(treatment);
		}

		CustomerAppointmentDomain appointmentDb = dashboardService.findAppontmentDetailById(appointment.getId());
		Integer maxCount = dashboardService.findLastVisitOfCustomerByCustomerId(appointment.getCustomerId(),
				AppointmentType.getAppointmentType(appointment.getTypeOfAppointmentString()));
		appointmentDb.setVisitNumber(maxCount + 1);
		appointmentDb.setIsVisitDone("YES");
		appointmentDb.setVisitDateAndTime(new Date());
		CustomerAppointmentDomain savedObject = dashboardService.saveAppointment(appointmentDb);
		actionResponse.setDocument(savedObject);
		actionResponse.setActionResponse(ActionStatus.SUCCESS);
		mMap.add("success", "User Appointment Succesfull");

		return new ResponseEntity<ActionResponse<CustomerAppointmentDomain>>(actionResponse, mMap, HttpStatus.OK);
	}

	@GetMapping(value = "/getpaymentpending")
	public ResponseEntity<ActionResponse<List<InvoiceDomain>>> getPaymentPendingCustomerList(HttpServletRequest request,
			Principal user, BindingResult result, HttpServletResponse response) {
		ActionResponse<List<InvoiceDomain>> actionResponse = new ActionResponse<List<InvoiceDomain>>();
		MultiValueMap<String, String> mMap = new LinkedMultiValueMap<>();
		List<InvoiceDomain> invoiceDetailList = new ArrayList<>();
		invoiceDetailList = dashboardService.findCustomerWithPaymentPending();
		actionResponse.setDocument(invoiceDetailList);
		return new ResponseEntity<ActionResponse<List<InvoiceDomain>>>(actionResponse, mMap, HttpStatus.OK);
	}

	@GetMapping(value = "/getpateintqueuelist")
	public ResponseEntity<ActionResponse<List<CustomerAppointmentDomain>>> getPateintsQueueList(
			HttpServletRequest request, Principal user, HttpServletResponse response) throws ParseException {
		ActionResponse<List<CustomerAppointmentDomain>> actionResponse = new ActionResponse<List<CustomerAppointmentDomain>>();
		MultiValueMap<String, String> mMap = new LinkedMultiValueMap<>();
		List<CustomerAppointmentDomain> appointmentList = new ArrayList<CustomerAppointmentDomain>();
		List<AppointmentType> appointmentTypeList = new ArrayList<AppointmentType>();
		appointmentTypeList.add(AppointmentType.getAppointmentType("Treatment ECP"));
		appointmentTypeList.add(AppointmentType.getAppointmentType("Treatment BCA"));
		appointmentList = dashboardService.getAppointmentPendingList(appointmentTypeList);
		for(CustomerAppointmentDomain appoint:appointmentList)
		{
			CustomerDetail customerDetail = customerService.findCustomerDetailById(appoint.getCustomerId());
			appoint.setCustomerName(customerDetail.getFirstName() + " " + customerDetail.getLastName());
		}
		actionResponse.setDocument(appointmentList);
		return new ResponseEntity<ActionResponse<List<CustomerAppointmentDomain>>>(actionResponse, mMap, HttpStatus.OK);

	}

	@GetMapping(value = "/getnewjoinee")
	public ResponseEntity<ActionResponse<List<CustomerDetail>>> getNewjoineeListForToday(HttpServletRequest request,
			Principal user, BindingResult result, HttpServletResponse response) {
		ActionResponse<List<CustomerDetail>> actionResponse = new ActionResponse<List<CustomerDetail>>();
		MultiValueMap<String, String> mMap = new LinkedMultiValueMap<>();
		List<CustomerDetail> customerDetailList = new ArrayList<>();
		customerDetailList = dashboardService.findTodayJoinedCustomerList();
		actionResponse.setDocument(customerDetailList);
		return new ResponseEntity<ActionResponse<List<CustomerDetail>>>(actionResponse, mMap, HttpStatus.OK);

	}

	@GetMapping(value = "/getinhouseappointments")
	public ResponseEntity<ActionResponse<List<CustomerAppointmentDomain>>> getInHouseAppointmentList(
			HttpServletRequest request, Principal user, BindingResult result, HttpServletResponse response) {
		ActionResponse<List<CustomerAppointmentDomain>> actionResponse = new ActionResponse<List<CustomerAppointmentDomain>>();
		MultiValueMap<String, String> mMap = new LinkedMultiValueMap<>();
		List<CustomerAppointmentDomain> appointmentList = new ArrayList<CustomerAppointmentDomain>();
		List<AppointmentType> appointmentTypeList = new ArrayList<AppointmentType>();
		appointmentTypeList.add(AppointmentType.getAppointmentType("In House"));
		appointmentList = dashboardService.getAppointmentPendingList(appointmentTypeList);
		actionResponse.setDocument(appointmentList);
		return new ResponseEntity<ActionResponse<List<CustomerAppointmentDomain>>>(actionResponse, mMap, HttpStatus.OK);
	}

	@PostMapping(value = "/updateandcompleteschedule")
	// @PreAuthorize("hasRole('ADMIN')")
	public  ResponseEntity<ActionResponse<CustomerAppointmentDomain>>  updateTreatmentSchedule(/* @Valid */
			@RequestBody CustomerAppointmentDomain appointment, HttpServletRequest request, Principal user,
			BindingResult result, HttpServletResponse response) {
		
		ActionResponse<CustomerAppointmentDomain> actionResponse = new ActionResponse<CustomerAppointmentDomain>();
		MultiValueMap<String, String> mMap = new LinkedMultiValueMap<>();
	
		TreatmentPlanDomain treatment = dashboardService.findTreatmentPlanById(appointment.getTreatmentPlanId());
		Duration dt = null;
		if(treatment.getTime()!=null) {
		 dt  = treatment.getTime().plus(Duration.ofMinutes(appointment.getDuration().longValue()));
		}else {
			dt = Duration.ofMinutes(appointment.getDuration().longValue());
		}
		treatment.setTime(dt);
		if(treatment.getTreatmentPlanDetailsList() != null && !treatment.getTreatmentPlanDetailsList().isEmpty()) {
		for(TreatmentPlanDetailDomain detail:treatment.getTreatmentPlanDetailsList()) {
			
			if(detail.getId().equals(appointment.getTreatmentDetailPlanId())) {
				detail.setComplaints(appointment.getPostScheduleDescription());
				detail.setDuration(Duration.ofMinutes(appointment.getDuration().longValue()));
				detail.setIsTreatmentDone(Constants.COMPLETED);
				detail.setMachineNo(appointment.getMachineNo());
				detail.setTreatmentDate(appointment.getStart());
				detail.setTreatmentType(AppointmentType.TREATMENT_ECP.getAppointmentString());
			}
		}
		
		dashboardService.saveTreatmentPlan(treatment);
		CustomerAppointmentDomain appointmentDb = dashboardService.findAppontmentDetailById(appointment.getId());
		appointmentDb.setIsVisitDone(Constants.COMPLETED);
		appointmentDb.setVisitDateAndTime(appointment.getStart());	
		appointmentDb.setTimeInDuration(Duration.ofMinutes(appointment.getDuration().longValue()));
		CustomerAppointmentDomain appointmentSavedDb = dashboardService.saveAppointment(appointmentDb);
		actionResponse.setDocument(appointmentSavedDb);
		actionResponse.setActionResponse(ActionStatus.SUCCESS);
		mMap.add("success", "User Created Successfully in Database");
		}
		
	return new ResponseEntity<ActionResponse<CustomerAppointmentDomain>>(actionResponse, mMap, HttpStatus.OK);
	}

	@GetMapping(value = "/gettreatmentpendinglist")
	public void getTreatmentPendingPlanList() {

	}

	@GetMapping(value = "/getlowstocks")
	public void getLowStockList() {

	}

	public boolean assignAndValidateAppointment(Integer noMachine, Integer dateNo,Date appointmentDate) {
		
		List<TreatmentPlanDetailDomain> listOfAllTreatmentPlan = dashboardService.getAllTreatmentPendingForDate(appointmentDate);
		boolean isExist  = false;
		
		 if(listOfAllTreatmentPlan != null && !listOfAllTreatmentPlan.isEmpty()) {
		  for(TreatmentPlanDetailDomain treatmentplan: listOfAllTreatmentPlan) {
			  Integer no = treatmentplan.getMachineNo(); 
			  Integer date = null; 
			  try {
				  LocalDateTime local =
				  LocalDateTime.ofInstant(treatmentplan.getTreatmentScheduledDate().toInstant()
				  , ZoneId.systemDefault()); 
				  date = local.getHour();
				  
				  if(no.equals(noMachine) && dateNo.equals(date)) {
					  isExist = true;
				  }
				  }catch(DateTimeException e) {
				  
				  } 
			  }
		 }
		return isExist;
}
	 public  Date parse( Date dateVal ) throws java.text.ParseException {
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
			dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			 String input = dateFormat.format(dateVal);
			
	        //NOTE: SimpleDateFormat uses GMT[-+]hh:mm for the TZ which breaks
	        //things a bit.  Before we go on we have to repair this.
	        SimpleDateFormat df = new SimpleDateFormat( "yyyy-MM-dd'T'HH:mm:ssz" );
	        
	        //this is zero time so we need to add that TZ indicator for 
	        if ( input.endsWith( "Z" ) ) {
	            input = input.substring( 0, input.length() - 1) + "GMT+05:30";
	        } else {
	            int inset = 6;
	        
	            String s0 = input.substring( 0, input.length() - inset );
	            String s1 = input.substring( input.length() - inset, input.length() );

	            input = s0 + "GMT" + s1;
	        }
	        
	        return df.parse( input );
	        
	    }
	 public  String deParse( Date dateVal ) throws java.text.ParseException {
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
			// String input = dateFormat.format(dateVal);
			
	        //NOTE: SimpleDateFormat uses GMT[-+]hh:mm for the TZ which breaks
	        //things a bit.  Before we go on we have to repair this.
	        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
	        String input   =   df.format( dateVal );
		
	        if ( input.endsWith( "Z" ) ) {
	            input = input.substring( 0, input.length() - 1) + "GMT+05:30";
	        } else {
	            int inset = 6;
	        
	            String s0 = input.substring( 0, input.length() - inset );
	            String s1 = input.substring( input.length() - inset, input.length() );

	            input = s0 + "GMT" + s1;
	        }
	        return input;
	        
	    }

}
