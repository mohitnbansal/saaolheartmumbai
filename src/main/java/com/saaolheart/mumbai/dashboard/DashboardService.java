package com.saaolheart.mumbai.dashboard;

import java.security.cert.PKIXRevocationChecker.Option;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saaolheart.mumbai.common.utility.Constants;
import com.saaolheart.mumbai.customer.AppointmentConstants;
import com.saaolheart.mumbai.customer.AppointmentType;
import com.saaolheart.mumbai.customer.CustomerAppointmentDomain;
import com.saaolheart.mumbai.customer.CustomerAppointmentRepo;
import com.saaolheart.mumbai.customer.CustomerDetail;
import com.saaolheart.mumbai.customer.CustomerRepository;
import com.saaolheart.mumbai.invoice.InvoiceDomain;
import com.saaolheart.mumbai.invoice.InvoiceRepository;
import com.saaolheart.mumbai.invoice.InvoiceStatuses;
import com.saaolheart.mumbai.store.stock.StockDomain;
import com.saaolheart.mumbai.store.stock.StockRepo;
import com.saaolheart.mumbai.treatment.treatmentplan.TreatmentPlanDetailDomain;
import com.saaolheart.mumbai.treatment.treatmentplan.TreatmentPlanDetailsRepo;
import com.saaolheart.mumbai.treatment.treatmentplan.TreatmentPlanDomain;
import com.saaolheart.mumbai.treatment.treatmentplan.TreatmentPlanRepository;
import com.saaolheart.mumbai.treatment.treatmentplan.TreatmentStatusConstants;

@Service
public class DashboardService {


	Logger logger = LoggerFactory.getLogger(DashboardService.class);
	
	@Autowired
	private CustomerAppointmentRepo appointmentRepo;
	
	@Autowired
	private CustomerRepository customerRepo;
	
	@Autowired
	private InvoiceRepository invoiceRepo;
	
	@Autowired
	private TreatmentPlanDetailsRepo treatmentDetailPlanRepo;
	

	@Autowired
	private TreatmentPlanRepository treatmentDetailRepo;
	
	@Autowired
	private TreatmentPlanRepository treatmentPlanRepo;
	

	@Autowired
	private StockRepo stockRepo;
	
	
	public CustomerAppointmentDomain findAppontmentDetailById(Long id) {
		Optional<CustomerAppointmentDomain> appointment = Optional.of(new CustomerAppointmentDomain());
		appointment = appointmentRepo.findById(id);
		return appointment.orElse(null);
	}
	
	public Integer findLastVisitOfCustomerByCustomerId(Long id,AppointmentType type) {
		Optional<Integer> max= null;
		try {
		max = appointmentRepo.findMaxVisitByCustomerId(id,type);
		}catch(Exception e) {
			logger.error("Unable to get Max value count of Appointment for Customer with Id"+id);
			
		}
		return max.orElse(new Integer(0));
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
		public List<CustomerAppointmentDomain> getAppointmentPendingList(List<AppointmentType> appointmentType) {
		
		Optional<List<CustomerAppointmentDomain>> customerAppointmentList = Optional.of(new ArrayList<CustomerAppointmentDomain>());
		try {
			
			customerAppointmentList = appointmentRepo.findByTypeOfAppointmentIn(appointmentType);
		}
		catch(Exception e) {
			logger.error("Customer Appointment List unable to fetch");
		}
		return customerAppointmentList.orElse(null);
	}

		public TreatmentPlanDetailDomain findTreatmentDetailPlanById(Long treatmentPlanDetailId) {
			Optional<TreatmentPlanDetailDomain> treatmenPlanDetail = Optional.of(new TreatmentPlanDetailDomain());
			
			try {
				treatmenPlanDetail = treatmentDetailPlanRepo.findById(treatmentPlanDetailId);
			}
			catch(Exception e) {
				logger.error("Unable to fetch treamentplan by id"+ treatmentPlanDetailId);
			}
			return treatmenPlanDetail.orElse(null); 
			
		}

		public TreatmentPlanDomain findTreatmentPlanById(Long treatmentId) {
Optional<TreatmentPlanDomain> treatmenPlan = Optional.of(new TreatmentPlanDomain());
			
			try {
				treatmenPlan = treatmentPlanRepo.findById(treatmentId);
			}
			catch(Exception e) {
				logger.error("Unable to fetch treamentplan by id"+ treatmentId);
			}
			return treatmenPlan.orElse(null); 
		}

		public TreatmentPlanDomain saveTreatmentPlan(TreatmentPlanDomain treatment) {
			TreatmentPlanDomain treatmenPlan = new TreatmentPlanDomain();

			try {
				treatmenPlan = treatmentPlanRepo.save(treatment);
				treatmentPlanRepo.refresh(treatmenPlan);
			}
			catch(Exception e) {
				logger.error("Unable to Save treamentplan for customer by id"+ treatment.getCustomerId());
			}
			return treatmenPlan; 
			
		}

		public TreatmentPlanDetailDomain saveTreatmentDetailPlan(TreatmentPlanDetailDomain treatmentDetalPlanSave) {
			TreatmentPlanDetailDomain treatmenPlan = new TreatmentPlanDetailDomain();

			try {
				treatmenPlan = treatmentDetailPlanRepo.save(treatmentDetalPlanSave);
			}
			catch(Exception e) {
				logger.error("Unable to Save treamentplan for customer by id"+ treatmentDetalPlanSave.getTreatmentPlanId());
			}
			return treatmenPlan; 
		}

		public List<TreatmentPlanDetailDomain> getAllTreatmentPendingForDate(Date appointmentDate) {
			Optional<List<TreatmentPlanDetailDomain>> treatmentplanList = Optional.of(new ArrayList<TreatmentPlanDetailDomain>());

			try {
				treatmentplanList  = treatmentDetailPlanRepo.findByIsTreatmentDoneIgnoreCaseAndTreatmentScheduledDateEqualsAndTreatmentType(Constants.PENDING, appointmentDate, AppointmentConstants.ECP_TREATMENT);
			
			}catch(Exception e) {
				
			}
			return treatmentplanList.orElse(null);
		}

		public List<TreatmentPlanDomain> getTreatmentPendingCustomer() {
			Optional<List<TreatmentPlanDomain>> treatmentplanList = Optional.of(new ArrayList<TreatmentPlanDomain>());
try {
	List<String> status = new ArrayList<String>();
	status.add(TreatmentStatusConstants.PENDING);
	treatmentplanList = treatmentDetailRepo.findByTreatmentStatusIgnoreCaseIn(status);
}catch(Exception e) {
	logger.error("Unable to Save treamentplan for customer by id");

}

			
			return treatmentplanList.orElse(null);
		}

		public List<InvoiceDomain> getPaymentPendingCustomer() {
Optional<List<InvoiceDomain>> invocicePendingList = Optional.of(new ArrayList<>());
try {
	List<String> status = new ArrayList<String>();
	status.add(InvoiceStatuses.PARTIALLYPAID.getInvoiceStatuses());
	status.add(InvoiceStatuses.NOTPAID.getInvoiceStatuses());
	invocicePendingList = invoiceRepo.findByInvoiceStatusIgnoreCaseIn(status);
}catch(Exception e) {
	
}
			return invocicePendingList.orElse(null);
		}

		public List<StockDomain> findStockswithLowQty(Long limit) {
			Optional<List<StockDomain>> invoiceDomain = Optional.of(new ArrayList<StockDomain>());
			try {
				invoiceDomain = stockRepo.findByQtyOfStockAvailableGreaterThanEqual(limit);
			}catch(Exception e) {
				
			}
			return invoiceDomain.orElse(null);
		}
}
