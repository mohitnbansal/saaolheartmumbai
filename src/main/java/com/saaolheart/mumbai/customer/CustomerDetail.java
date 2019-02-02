package com.saaolheart.mumbai.customer;

import java.io.Serializable;
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
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.saaolheart.mumbai.security.domain.User;
import com.saaolheart.mumbai.treatment.ctangiography.CtAngioDetailsDomain;
import com.saaolheart.mumbai.treatment.doctorconsultation.DoctorConsultationDomain;
import com.saaolheart.mumbai.treatment.treatmentplan.TreatmentPlanDomain;

@Entity
@Table(name="CUSTOMER_DETAILS")
public class CustomerDetail implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column(name="FIRST_NAME")
	private String firstName;
	
	@Column(name="LAST_NAME")
	private String lastName;

	@Column(name="MIDDLE_NAME")
	private String middleName;
	
	@Column(name="ADDRESS")
	private String address;
	
	@Column(name="DATE_OF_CREATION")
	@CreationTimestamp
	private Date dateOfCreation;
	
	
	@Column(name="EMAIL_ID")
	private String emailId;
	
	@Column(name="GENDER")
	private String gender;
	
	@Column(name="MARTIAL_STATUS")
	private String martialStatus;
	
	@Column(name="DOB")
	private Date dob;
	
	@Column(name="MOBILE_NO")
	private Long mobileNo;
	
	@Column(name="ALT_MOBILE_NO")
	private Long altMobileNo;
	
	@Column(name="AGE")
	private Long age;
	
	@Column(name="VISITING_FOR")
	private String vistingFor; 
	
	@Column(name="AADHAR_NUMBER")
	private Long aadharNumber;
	
	@Column(name="OCCUPATION")
	private String occupation;
	
	@Column(name="LANDLINE_OFF")
	private String landlineOff;
	
	@Column(name="LANDLINE_RES")
	private String landlineRes;
	

	@Column(name="PAN_NUMBER")
	private String panNumber;
	
	
	@Column(name="CONSULTATION_NUMBER")
	private Long consultaionNumber;

	@Column(name="SAAOL_CODE")
	private String saaolCode;
	
	@OneToMany(cascade= {CascadeType.ALL},orphanRemoval=true)
	@JoinColumn(referencedColumnName="ID",name="CUSTOMER_ID")
	private List<CtAngioDetailsDomain> ctAngioDetailList;
	

	@OneToMany(cascade= {CascadeType.ALL},orphanRemoval=true)
	@JoinColumn(referencedColumnName="ID",name="CUSTOMER_ID")
	private List<DoctorConsultationDomain> doctorConsultationList;
	

	@OneToMany(cascade= {CascadeType.ALL},orphanRemoval=true)
	@JoinColumn(referencedColumnName="ID",name="CUSTOMER_ID")
	private List<TreatmentPlanDomain> treatmentPlanList;
	

	@Column(name="STATUS_OF_TREATMENT")
	private String statusOfTreatment;
	
	@Column(name="CUSTOMER_REF_ID")
	private String customerRefId;
	
	@Column(name="GENERATED_BY")
	private String generetedBy;
	
	@ManyToOne
	@JoinColumn(name="GENERATED_BY",referencedColumnName="username",insertable=false,updatable=false)
	private User generatedBy;
	
	
	

	public String getPanNumber() {
		return panNumber;
	}

	public void setPanNumber(String panNumber) {
		this.panNumber = panNumber;
	}

	public String getGeneretedBy() {
		return generetedBy;
	}

	public void setGeneretedBy(String generetedBy) {
		this.generetedBy = generetedBy;
	}

	public User getGeneratedBy() {
		return generatedBy;
	}

	public void setGeneratedBy(User generatedBy) {
		this.generatedBy = generatedBy;
	}

	public String getCustomerRefId() {
		return customerRefId;
	}

	public void setCustomerRefId(String customerRefId) {
		this.customerRefId = customerRefId;
	}

	public List<CtAngioDetailsDomain> getCtAngioDetailList() {
		return ctAngioDetailList;
	}

	public void setCtAngioDetailList(List<CtAngioDetailsDomain> ctAngioDetailList) {
		this.ctAngioDetailList = ctAngioDetailList;
	}

	public List<DoctorConsultationDomain> getDoctorConsultationList() {
		return doctorConsultationList;
	}

	public void setDoctorConsultationList(List<DoctorConsultationDomain> doctorConsultationList) {
		this.doctorConsultationList = doctorConsultationList;
	}

	public List<TreatmentPlanDomain> getTreatmentPlanList() {
		return treatmentPlanList;
	}

	public void setTreatmentPlanList(List<TreatmentPlanDomain> treatmentPlanList) {
		this.treatmentPlanList = treatmentPlanList;
	}

	public String getStatusOfTreatment() {
		return statusOfTreatment;
	}

	public void setStatusOfTreatment(String statusOfTreatment) {
		this.statusOfTreatment = statusOfTreatment;
	}

	public Long getAge() {
		return age;
	}

	public void setAge(Long age) {
		this.age = age;
	}

	public Long getConsultaionNumber() {
		return consultaionNumber;
	}

	public void setConsultaionNumber(Long consultaionNumber) {
		this.consultaionNumber = consultaionNumber;
	}

	public String getSaaolCode() {
		return saaolCode;
	}

	public void setSaaolCode(String saaolCode) {
		this.saaolCode = saaolCode;
	}

	public String getLandlineOff() {
		return landlineOff;
	}

	public void setLandlineOff(String landlineOff) {
		this.landlineOff = landlineOff;
	}

	public String getLandlineRes() {
		return landlineRes;
	}

	public void setLandlineRes(String landlineRes) {
		this.landlineRes = landlineRes;
	}

	public void setVistingFor(String vistingFor) {
		this.vistingFor = vistingFor;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMartialStatus() {
		return martialStatus;
	}

	public void setMartialStatus(String martialStatus) {
		this.martialStatus = martialStatus;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}


	public Long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(Long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public Long getAltMobileNo() {
		return altMobileNo;
	}

	public void setAltMobileNo(Long altMobileNo) {
		this.altMobileNo = altMobileNo;
	}

	public String getVistingFor() {
		return vistingFor;
	}

	public Long getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(Long aadharNumber) {
		this.aadharNumber = aadharNumber;
	}


	
	
	
	

}
