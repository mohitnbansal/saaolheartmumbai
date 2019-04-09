package com.saaolheart.mumbai.treatment.treatmentplan;

import java.io.Serializable;
import java.time.Duration;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.saaolheart.mumbai.customer.CustomerDetail;
import com.saaolheart.mumbai.invoice.InvoiceDomain;
import com.saaolheart.mumbai.masters.treatment.TreatmentTypeMasterDomain;

@Entity
@Table(name="TREATMENT_PLAN")
public class TreatmentPlanDomain implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="TREATMENT_TYPE_MASTER_ID")
	private Long treatmentTypeMasterId;
	
	@Column(name="TREATMENT_INV_DATE")
	private Date treatmentInvoiceDate;
	
	
	@ManyToOne
	@JoinColumn(name="TREATMENT_TYPE_MASTER_ID",referencedColumnName="ID",insertable=false,updatable=false)
	private TreatmentTypeMasterDomain treatmentMaster;
	
	@Column(name="TREATMENT_STATUS")
	private String treatmentStatus;

	@OneToMany(cascade= {CascadeType.ALL},orphanRemoval=true)
	@JoinColumn(name="TREATMENT_PLAN_ID",referencedColumnName="ID")
	@Fetch(value=FetchMode.SELECT)
	private List<TreatmentPlanDetailDomain> treatmentPlanDetailsList;
	

	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="INVOICE_ID",referencedColumnName="ID")
	private InvoiceDomain invoiceDomain;
	
	@Column(name="NO_SITTINGS")
	private Integer noOfSittings;
	
	@Column(name="TOTAL_DURATION")
	private Duration time;
	
	@Column(name="CUSTOMER_ID")
	private Long customerId;	
	



	@Transient
	private Double invoiceTotalamt;

	
	@Transient
	private Long invoiceMasterTypeId;
	

	@Transient
	private CustomerDetail customerDetails;
	
	
	
	
	
	
	public CustomerDetail getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetail customerDetails) {
		this.customerDetails = customerDetails;
	}

	public Integer getNoOfSittings() {
		return noOfSittings;
	}

	public void setNoOfSittings(Integer noOfSittings) {
		this.noOfSittings = noOfSittings;
	}

	public Duration getTime() {
		return time;
	}

	public void setTime(Duration time) {
		this.time = time;
	}

	public Date getTreatmentInvoiceDate() {
		return treatmentInvoiceDate;
	}

	public void setTreatmentInvoiceDate(Date treatmentInvoiceDate) {
		this.treatmentInvoiceDate = treatmentInvoiceDate;
	}

	public Double getInvoiceTotalamt() {
		return invoiceTotalamt;
	}

	public void setInvoiceTotalamt(Double invoiceTotalamt) {
		this.invoiceTotalamt = invoiceTotalamt;
	}

	public Long getInvoiceMasterTypeId() {
		return invoiceMasterTypeId;
	}

	public void setInvoiceMasterTypeId(Long invoiceMasterTypeId) {
		this.invoiceMasterTypeId = invoiceMasterTypeId;
	}

	public Long getTreatmentTypeMasterId() {
		return treatmentTypeMasterId;
	}

	public InvoiceDomain getInvoiceDomain() {
		return invoiceDomain;
	}

	public void setInvoiceDomain(InvoiceDomain invoiceDomain) {
		this.invoiceDomain = invoiceDomain;
	}

	public void setTreatmentTypeMasterId(Long treatmentTypeMasterId) {
		this.treatmentTypeMasterId = treatmentTypeMasterId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public List<TreatmentPlanDetailDomain> getTreatmentPlanDetailsList() {
		return treatmentPlanDetailsList;
	}

	public void setTreatmentPlanDetailsList(List<TreatmentPlanDetailDomain> treatmentPlanDetailsList) {
		this.treatmentPlanDetailsList = treatmentPlanDetailsList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public TreatmentTypeMasterDomain getTreatmentMaster() {
		return treatmentMaster;
	}

	public void setTreatmentMaster(TreatmentTypeMasterDomain treatmentMaster) {
		this.treatmentMaster = treatmentMaster;
	}

	public String getTreatmentStatus() {
		return treatmentStatus;
	}

	public void setTreatmentStatus(String treatmentStatus) {
		this.treatmentStatus = treatmentStatus;
	}
	
	
	
}
