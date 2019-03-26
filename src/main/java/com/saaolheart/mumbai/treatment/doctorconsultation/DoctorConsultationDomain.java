package com.saaolheart.mumbai.treatment.doctorconsultation;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;

import com.saaolheart.mumbai.invoice.InvoiceDomain;
import com.saaolheart.mumbai.masters.treatment.TreatmentTypeMasterDomain;

@Entity
@Table(name="DOCTOR_CONSULTATION")
public class DoctorConsultationDomain implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	
	@Column(name="CONSULTATION_DATE")
	private Date consulationDate;
	
	@Column(name="CONSULTATION_BY")
	private String consultationBy;
	
	@Column(name="DAIGNOSIS_SUMMARY")
	private String daignosisSummary;
	
	@Column(name="TEST_SUGGESTED")
	private String testSuggested;
	
	@Column(name="TYPE_OF_TREATMENT")
	private Long typeOfTreatement;
	
	@ManyToOne
	@JoinColumn(name="TYPE_OF_TREATMENT",referencedColumnName="ID",insertable=false,updatable=false)
	private TreatmentTypeMasterDomain typeOfTreatementDomain;
	
	@OneToOne(cascade = {CascadeType.ALL})
	@JoinColumn(name="INVOICE_ID",referencedColumnName="ID")
	private InvoiceDomain invoiceDomain;
	
	@Column(name="CUSTOMER_ID")
	private Long customerId;


	@Transient
	private Double invoiceTotalamt;

	
	@Transient
	private Long invoiceMasterTypeId;
	
	
	
	

	public Long getTypeOfTreatement() {
		return typeOfTreatement;
	}

	public void setTypeOfTreatement(Long typeOfTreatement) {
		this.typeOfTreatement = typeOfTreatement;
	}

	public Long getInvoiceMasterTypeId() {
		return invoiceMasterTypeId;
	}

	public void setInvoiceMasterTypeId(Long invoiceMasterTypeId) {
		this.invoiceMasterTypeId = invoiceMasterTypeId;
	}

	public Double getInvoiceTotalamt() {
		return invoiceTotalamt;
	}

	public void setInvoiceTotalamt(Double invoiceTotalamt) {
		this.invoiceTotalamt = invoiceTotalamt;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public InvoiceDomain getInvoiceDomain() {
		return invoiceDomain;
	}

	public void setInvoiceDomain(InvoiceDomain invoiceDomain) {
		this.invoiceDomain = invoiceDomain;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getConsulationDate() {
		return consulationDate;
	}

	public void setConsulationDate(Date consulationDate) {
		this.consulationDate = consulationDate;
	}

	public String getConsultationBy() {
		return consultationBy;
	}

	public void setConsultationBy(String consultationBy) {
		this.consultationBy = consultationBy;
	}

	public String getDaignosisSummary() {
		return daignosisSummary;
	}

	public void setDaignosisSummary(String daignosisSummary) {
		this.daignosisSummary = daignosisSummary;
	}

	public String getTestSuggested() {
		return testSuggested;
	}

	public void setTestSuggested(String testSuggested) {
		this.testSuggested = testSuggested;
	}

	public TreatmentTypeMasterDomain getTypeOfTreatementDomain() {
		return typeOfTreatementDomain;
	}

	public void setTypeOfTreatementDomain(TreatmentTypeMasterDomain typeOfTreatementDomain) {
		this.typeOfTreatementDomain = typeOfTreatementDomain;
	}

	
	
	
	

}
