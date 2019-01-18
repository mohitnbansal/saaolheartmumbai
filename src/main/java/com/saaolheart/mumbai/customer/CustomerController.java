package com.saaolheart.mumbai.customer;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.saaolheart.mumbai.common.response.ActionResponse;
import com.saaolheart.mumbai.common.response.ActionStatus;
import com.saaolheart.mumbai.common.utility.CommonUtilities;

@RestController
@RequestMapping(value="/customer")
@EnableAutoConfiguration
public class CustomerController {
	
	Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
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
	@PostMapping(value="/addcustomer")
	//@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ActionResponse<CustomerDetail>> saveCustomer(/* @Valid */ @RequestBody CustomerDetail customer,
			HttpServletRequest request,Principal user,
			BindingResult result,HttpServletResponse response){
ActionResponse<CustomerDetail> actionResponse = new ActionResponse<CustomerDetail>();
MultiValueMap<String, String> mMap = new LinkedMultiValueMap<>();
		List<CustomerDetail> custDb = null;
		
		/**
		 * User Validation from DB isUserExist
		 */
		if(customer!=null && customer.getId()==null) {
		 custDb = customerService.findCustomerByPhoneNo(customer.getMobileNo());
		 if(custDb!=null && !custDb.isEmpty()) {
			 mMap.add("error", "User Already Exist in Database");
			 actionResponse.setDocument(customer);
			 return new ResponseEntity<ActionResponse<CustomerDetail>>(actionResponse,mMap,HttpStatus.BAD_REQUEST);
		 }
		}
		customer.setGeneretedBy(user.getName());
		customer = customerService.saveCustomer(customer);
		if(customer!=null && customer.getId()!=null) {
			String requestRefernceNumber = CommonUtilities.getRequestReference(customer.getId(), customer.getDateOfCreation());
			customer.setCustomerRefId(requestRefernceNumber);
			customer = customerService.saveCustomer(customer);
			actionResponse.setDocument(customer);
			actionResponse.setActionResponse(ActionStatus.SUCCESS);
			mMap.add("success", "User Created Successfully in Database");
		}
		
		return new ResponseEntity<ActionResponse<CustomerDetail>>(actionResponse,mMap,HttpStatus.OK);
		
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
		cust = customerService.getCustomerDetailById(id);
		if(cust != null && cust.getId()!=null) {
			return new ResponseEntity<CustomerDetail>(cust,HttpStatus.OK);
		}else {
			return new ResponseEntity<CustomerDetail>(cust,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
}
