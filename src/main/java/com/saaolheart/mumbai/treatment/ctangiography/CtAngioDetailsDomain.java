package com.saaolheart.mumbai.treatment.ctangiography;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.saaolheart.mumbai.invoice.InvoiceDomain;

/**
 * @author mohit
 *
 */
@Entity
@Table(name="CT_ANGIO_DETAILS")
public class CtAngioDetailsDomain implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="CENTER_NAME")
	private String centerName;
	
	@Column(name="REF_DATE")
	private Date refDate;
	
	@Column(name="SCAN_TEST_NAME")
	private String scanTestName;
	
	

	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="INVOICE_ID",referencedColumnName="ID")
	private InvoiceDomain invoiceDomain;
	
	@Column(name="ADD_LINE_1")
	private String addLine1;
	
	@Column(name="ADD_LINE_2")
	private String addLine2;
	
	@Column(name="PINCODE")
	private String pincode;
	

	@Column(name="CITY")
	private String city;	
	

	@Column(name="CONTACT_NO")
	private Long contactNo;	
		
	@Column(name="LANDMARK")
	private String landmark;	
	
	@Column(name="CUSTOMER_ID")
	private Long customerId;
	
	@Transient
	private Long invoiceMasterTypeId;

	@Transient
	private Double invoiceTotalamt;
	
	
	

	public Double getInvoiceTotalamt() {
		return invoiceTotalamt;
	}

	public void setInvoiceTotalamt(Double invoiceTotalamt) {
		this.invoiceTotalamt = invoiceTotalamt;
	}

	

	public Long getContactNo() {
		return contactNo;
	}

	public void setContactNo(Long contactNo) {
		this.contactNo = contactNo;
	}

	public Long getInvoiceMasterTypeId() {
		return invoiceMasterTypeId;
	}

	public void setInvoiceMasterTypeId(Long invoiceMasterTypeId) {
		this.invoiceMasterTypeId = invoiceMasterTypeId;
	}

	
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public Date getRefDate() {
		return refDate;
	}

	public void setRefDate(Date refDate) {
		this.refDate = refDate;
	}

	public String getScanTestName() {
		return scanTestName;
	}

	public void setScanTestName(String scanTestName) {
		this.scanTestName = scanTestName;
	}

	


	public InvoiceDomain getInvoiceDomain() {
		return invoiceDomain;
	}

	public void setInvoiceDomain(InvoiceDomain invoiceDomain) {
		this.invoiceDomain = invoiceDomain;
	}

	public String getAddLine1() {
		return addLine1;
	}

	public void setAddLine1(String addLine1) {
		this.addLine1 = addLine1;
	}

	public String getAddLine2() {
		return addLine2;
	}

	public void setAddLine2(String addLine2) {
		this.addLine2 = addLine2;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}
	
	
	
	
	
}
