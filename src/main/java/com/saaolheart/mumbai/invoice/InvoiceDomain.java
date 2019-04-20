package com.saaolheart.mumbai.invoice;

import java.io.Serializable;
import java.util.ArrayList;
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

import org.springframework.data.annotation.CreatedDate;

import com.saaolheart.mumbai.customer.CustomerDetail;
import com.saaolheart.mumbai.masters.invoice.InvoiceTypeMaster;
import com.saaolheart.mumbai.security.domain.User;
import com.saaolheart.mumbai.treatment.treatmentplan.TreatmentPlanDomain;

/**
 * @author mohit
 *
 */
@Entity
@Table(name="INVOICE")
public class InvoiceDomain  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 public  InvoiceDomain() {
	this.invoiceReciptList = new ArrayList<InvoiceRecieptDetailDomain>();
}
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@OneToMany(cascade= {CascadeType.ALL})
	@JoinColumn(name="INVOICE_ID",referencedColumnName="ID")
	private List<InvoiceRecieptDetailDomain> invoiceReciptList;
	
	@Column(name="INVOICE_TYPE_ID")
	private Long invoiceTypeId;

	@ManyToOne
	@JoinColumn(referencedColumnName="ID",name="INVOICE_TYPE_ID",insertable=false,updatable=false)
	private InvoiceTypeMaster invoiceType;
	
	@Column(name="TOTAL_INVOICE_AMT")
	private Double totalInvoiceAmt;
	
	@Column(name="BALANCE_AMT")
	private Double balanceAmt;
	
	@Column(name="DISCOUNT_AMT")
	private Double discountAmt;
	
	@Column(name="INVOICE_STATUS")
	private String invoiceStatus;
	
	@Column(name="GENERATED_BY")
	private String generetedByName;
	
	@Column(name="CUSTOMER_ID")
	private Long customerId;

	@Transient
	private CustomerDetail customerDetail;
	
	@OneToOne
	@JoinColumn(name="GENERATED_BY",referencedColumnName="username",insertable=false,updatable=false)
	private User generatedBy;

	@Column(name="CREATED_DATE")
	@CreatedDate
	private Date createdDate;
	
	@Transient
	private String bankName;
	
	@Transient
	private String referenceNumber;
	
	@Transient
	private Double paymentAmount;
	
	@Transient
	private String paymentMode;
	
	@Transient
	private Double newInvoiceAmountInCaseofCancel;
	
	@Transient
	private TreatmentPlanDomain treatmentPlan;
	

	@Transient
	private CustomerDetail customerDetails;
	
	@Transient
	private String customerName;
	
	
	@Transient
	private String gender;
	
	@Transient
	private Long mobileNo;
	
	@Transient
	private String enrolledFor;
		
	@Transient
	private String cancelInvoice;
	
	
	
	

	public String getCancelInvoice() {
		return cancelInvoice;
	}

	public void setCancelInvoice(String cancelInvoice) {
		this.cancelInvoice = cancelInvoice;
	}

	public TreatmentPlanDomain getTreatmentPlan() {
		return treatmentPlan;
	}

	public void setTreatmentPlan(TreatmentPlanDomain treatmentPlan) {
		this.treatmentPlan = treatmentPlan;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	

	public Long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEnrolledFor() {
		return enrolledFor;
	}

	public void setEnrolledFor(String enrolledFor) {
		this.enrolledFor = enrolledFor;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public CustomerDetail getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetail customerDetails) {
		this.customerDetails = customerDetails;
	}

	public Double getNewInvoiceAmountInCaseofCancel() {
		return newInvoiceAmountInCaseofCancel;
	}

	public void setNewInvoiceAmountInCaseofCancel(Double newInvoiceAmountInCaseofCancel) {
		this.newInvoiceAmountInCaseofCancel = newInvoiceAmountInCaseofCancel;
	}

	public CustomerDetail getCustomerDetail() {
		return customerDetail;
	}

	public void setCustomerDetail(CustomerDetail customerDetail) {
		this.customerDetail = customerDetail;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Long getInvoiceTypeId() {
		return invoiceTypeId;
	}

	public void setInvoiceTypeId(Long invoiceTypeId) {
		this.invoiceTypeId = invoiceTypeId;
	}

	


	public String getGeneretedByName() {
		return generetedByName;
	}

	public void setGeneretedByName(String generetedByName) {
		this.generetedByName = generetedByName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public List<InvoiceRecieptDetailDomain> getInvoiceReciptList() {
		return invoiceReciptList;
	}

	public void setInvoiceReciptList(List<InvoiceRecieptDetailDomain> invoiceReciptList) {
		this.invoiceReciptList.clear();
		this.invoiceReciptList.addAll(invoiceReciptList);
//		this.invoiceReciptList = invoiceReciptList;
	}

	public InvoiceTypeMaster getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(InvoiceTypeMaster invoiceType) {
		this.invoiceType = invoiceType;
	}

	public Double getTotalInvoiceAmt() {
		return totalInvoiceAmt;
	}

	public void setTotalInvoiceAmt(Double totalInvoiceAmt) {
		this.totalInvoiceAmt = totalInvoiceAmt;
	}

	public Double getBalanceAmt() {
		return balanceAmt;
	}

	public void setBalanceAmt(Double balanceAmt) {
		this.balanceAmt = balanceAmt;
	}

	public Double getDiscountAmt() {
		return discountAmt;
	}

	public void setDiscountAmt(Double discountAmt) {
		this.discountAmt = discountAmt;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public User getGeneratedBy() {
		return generatedBy;
	}

	public void setGeneratedBy(User generatedBy) {
		this.generatedBy = generatedBy;
	}
	
	
	
	
}
