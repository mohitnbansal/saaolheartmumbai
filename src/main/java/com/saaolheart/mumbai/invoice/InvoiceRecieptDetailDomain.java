package com.saaolheart.mumbai.invoice;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.saaolheart.mumbai.security.domain.User;

@Entity
@Table(name="INVOICE_RECIEPT_DETAIL")
public class InvoiceRecieptDetailDomain implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="INVOICE_ID")
	private Long invoiceId;
	
	@Column(name="IS_PRINTED")
	private String isPrinted;
	
	@Column(name="PAYMENT_MODE")
	private String paymentMode;
	
	@Column(name="PAYMENT_REFERENCE_NO")
	private String paymentReferenceNo;
	
	@Column(name="PAYMENT_DATE")
	private Date paymentDate;
	
	@Column(name="RECIEVED_BY")
	private Long recievedBy;
	
	@ManyToOne
	@JoinColumn(name="RECIEVED_BY",referencedColumnName="ID",insertable=false,updatable=false)
	private User recievedByUser;
	
	@Column(name="IS_EMAILED")
	private String isEmailed;
	
	@Column(name="EMAILED_TO_ID")
	private String emailedToId;

	@Column(name="REFUND_AMOUNT")
	private Double refundAmount;
	
	
	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Long invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getIsPrinted() {
		return isPrinted;
	}

	public void setIsPrinted(String isPrinted) {
		this.isPrinted = isPrinted;
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

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Long getRecievedBy() {
		return recievedBy;
	}

	public void setRecievedBy(Long recievedBy) {
		this.recievedBy = recievedBy;
	}

	public User getRecievedByUser() {
		return recievedByUser;
	}

	public void setRecievedByUser(User recievedByUser) {
		this.recievedByUser = recievedByUser;
	}

	public String getIsEmailed() {
		return isEmailed;
	}

	public void setIsEmailed(String isEmailed) {
		this.isEmailed = isEmailed;
	}

	public String getEmailedToId() {
		return emailedToId;
	}

	public void setEmailedToId(String emailedToId) {
		this.emailedToId = emailedToId;
	}
	
	
	
	
	

}
