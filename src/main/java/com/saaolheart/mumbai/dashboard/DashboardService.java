package com.saaolheart.mumbai.dashboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saaolheart.mumbai.customer.AppointmentStatusesConstants;
import com.saaolheart.mumbai.customer.AppointmentType;
import com.saaolheart.mumbai.customer.CustomerAppointmentDomain;
import com.saaolheart.mumbai.customer.CustomerAppointmentRepo;
import com.saaolheart.mumbai.customer.CustomerDetail;
import com.saaolheart.mumbai.customer.CustomerRepository;
import com.saaolheart.mumbai.invoice.InvoiceDomain;
import com.saaolheart.mumbai.invoice.InvoiceRepository;

@Service
public class DashboardService {


	Logger logger = LoggerFactory.getLogger(DashboardService.class);
	
	@Autowired
	private CustomerAppointmentRepo appointmentRepo;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private InvoiceRepository invoiceRepo;
	
	
	public CustomerAppointmentDomain findAppontmentDetailById(Long id) {
		Optional<CustomerAppointmentDomain> appointment = Optional.of(new CustomerAppointmentDomain());
		appointment = appointmentRepo.findById(id);
		return appointment.orElse(null);
	}
	
	public Integer findLastVisitOfCustomerByCustomerId(Long id,AppointmentType type) {
		Integer max= 0;
		try {
		max = appointmentRepo.findMaxVisitByCustomerId(id,type);
		}catch(Exception e) {
			logger.error("Unable to get Max value count of Appointment for Customer with Id"+id);
			max= 0;
		}
		return max;
	}

	public CustomerAppointmentDomain saveAppointment(CustomerAppointmentDomain appointmentDb) {
		CustomerAppointmentDomain appointment = null;
		try {
			appointment = appointmentRepo.save(appointmentDb);
		}catch(Exception e) {
			logger.error("Couldnt Save Appointment for Customer" + appointmentDb.getCustomerId());
			
		}
		return appointment;
	}

	public List<CustomerDetail> findTodayJoinedCustomerList() {
		Optional<List<CustomerDetail>> list = Optional.of(new ArrayList<CustomerDetail>());
		try {
			list = customerRepo.findByDateOfCreation(new Date());
		}catch(Exception e) {
			logger.error("Couldnt Save Appointment for Customer" + new Date());
		}
		return list.orElse(null);
	}

	public List<InvoiceDomain> findCustomerWithPaymentPending() {
		Optional<List<InvoiceDomain>> list = Optional.of(new ArrayList<InvoiceDomain>());
		try {
			list = invoiceRepo.findByBalanceAmtGreaterThan(0D);
		}catch(Exception e) {
			logger.error("Couldnt Save Appointment for Customer" + new Date());
		}
		List<CustomerDetail> customerDetailList = new ArrayList<CustomerDetail>();
		for(InvoiceDomain inv:list.get()) {
			if(inv.getCustomerId() != null) {
				try {
			Optional<CustomerDetail> customer = customerRepo.findById(inv.getCustomerId());
			inv.setCustomerDetail(customer.get());
				}catch(Exception e) {
					logger.error("Couldnt Save Appointment for Customer" + new Date());
				}
			}		
		}
		/*
		 * List<CustomerDetail> customerList = list.get().stream().map(p->
		 * p.getCustomerDetail()).collect(Collectors.toList());
		 */
		return list.orElse(null);
	}

	public List<CustomerAppointmentDomain> getAppointmentPendingList() {
		
		Optional<List<CustomerAppointmentDomain>> customerAppointmentList = Optional.of(new ArrayList<CustomerAppointmentDomain>());
		try {
		customerAppointmentList = appointmentRepo.findByIsVisitDoneIgnoreCase(AppointmentStatusesConstants.PENDING);
		}
		catch(Exception e) {
			logger.error("Customer Appointment List unable to fetch");
		}
		return customerAppointmentList.orElse(null);
	}
}
