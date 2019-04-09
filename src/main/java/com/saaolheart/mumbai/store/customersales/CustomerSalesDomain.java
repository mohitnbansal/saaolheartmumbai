package com.saaolheart.mumbai.store.customersales;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.saaolheart.mumbai.customer.CustomerDetail;
import com.saaolheart.mumbai.invoice.InvoiceDomain;

@Entity
@Table(name="SALES_INV")
public class CustomerSalesDomain implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	
	@OneToMany(cascade= {CascadeType.ALL},orphanRemoval=true,fetch = FetchType.EAGER)
	@JoinColumn(referencedColumnName="ID",name="CUSTOMER_SALES_ID")
	@Fetch(value=FetchMode.SELECT)
	private List<CustomerPurchasesDomain> customerPurchasesList;
	
	@OneToOne(cascade= {CascadeType.ALL},orphanRemoval=true)
	@JoinColumn(referencedColumnName="ID",name="INVOICE_DOMAIN_ID")
	private InvoiceDomain invoiceOfPurchase;
	
	@Column(name="CUSTOMER_ID")
	private Long customerId;

	@Transient
	private Double totalInvoiceAmt;
	
	@Transient
	private String paymentMode;
	
	@Transient
	private String paymentReferenceNo;
	
	@Transient
	private String isEmailed;
	
	@Transient
	private Double refundAmount;
	
	@Transient
	private Double paymentAmount;
	
	@Transient
	private String bankName;
	
	@Transient
	private String isPrinted;
	
	@Transient
	private String customerName;
	
	@Transient
	private CustomerDetail customerDetails;
	
	
	
	

	public CustomerDetail getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetail customerDetails) {
		this.customerDetails = customerDetails;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getIsPrinted() {
		return isPrinted;
	}

	public void setIsPrinted(String isPrinted) {
		this.isPrinted = isPrinted;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public Double getTotalInvoiceAmt() {
		return totalInvoiceAmt;
	}

	public void setTotalInvoiceAmt(Double totalInvoiceAmt) {
		this.totalInvoiceAmt = totalInvoiceAmt;
	}

	public String getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getPaymentReferenceNo() {
		return paymentReferenceNo;
	}

	public void setPaymentReferenceNo(String paymentReferenceNo) {
		this.paymentReferenceNo = paymentReferenceNo;
	}

	public String getIsEmailed() {
		return isEmailed;
	}

	public void setIsEmailed(String isEmailed) {
		this.isEmailed = isEmailed;
	}

	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<CustomerPurchasesDomain> getCustomerPurchasesList() {
		return customerPurchasesList;
	}

	public void setCustomerPurchasesList(List<CustomerPurchasesDomain> customerPurchasesList) {
		this.customerPurchasesList = customerPurchasesList;
	}

	public InvoiceDomain getInvoiceOfPurchase() {
		return invoiceOfPurchase;
	}

	public void setInvoiceOfPurchase(InvoiceDomain invoiceOfPurchase) {
		this.invoiceOfPurchase = invoiceOfPurchase;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	
	
	

}
