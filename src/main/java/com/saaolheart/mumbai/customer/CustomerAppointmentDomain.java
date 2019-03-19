package com.saaolheart.mumbai.customer;


import java.io.Serializable;
import java.time.Duration;
import java.util.Comparator;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "APPOINTMENT_DOMAIN")
public class CustomerAppointmentDomain  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column(name="EXPECTED_TIME")
	private Date expectedTime;
	
	@Column(name="DT_OF_SCHEDULE")
	private Date dateOfScheduling;
	
	@Column(name="VISIT_NUMBER")
	private Integer visitNumber;
	
	@Column(name="IS_VISIT_DONE")
	private String isVisitDone;
	
	@Column(name="VISIT_DAT_TIM")
	private Date visitDateAndTime;
	
	@Column(name="VISITING_FOR_DESC")
	private String visitingForDescription;
	
	@Column(name="SCHEDULE_COUNT_NO")
	private Integer scheduledNumber;	
	
	
	@Enumerated(EnumType.STRING)
	@Column(name="TYPE_OF_APPOINTMENT")
	private AppointmentType typeOfAppointment;

	@Column(name="CUSTOMER_ID")
	private Long customerId;
	
	

	@Column(name="POST_DESCRIPTION")
	private String postScheduleDescription;
	
	@Transient
	private String typeOfAppointmentString;

	@Transient
	private Duration durationOfTreatment;
	
	@Transient
	private Integer machineNo;
	
	
	
	
	

	public String getPostScheduleDescription() {
		return postScheduleDescription;
	}


	public void setPostScheduleDescription(String postScheduleDescription) {
		this.postScheduleDescription = postScheduleDescription;
	}


	public Integer getMachineNo() {
		return machineNo;
	}


	public void setMachineNo(Integer machineNo) {
		this.machineNo = machineNo;
	}


	public Duration getDurationOfTreatment() {
		return durationOfTreatment;
	}


	public void setDurationOfTreatment(Duration durationOfTreatment) {
		this.durationOfTreatment = durationOfTreatment;
	}


	public String getTypeOfAppointmentString() {
		return typeOfAppointmentString;
	}


	public void setTypeOfAppointmentString(String typeOfAppointmentString) {
		this.typeOfAppointmentString = typeOfAppointmentString;
	}


	public Date getVisitDateAndTime() {
		return visitDateAndTime;
	}


	public void setVisitDateAndTime(Date visitDateAndTime) {
		this.visitDateAndTime = visitDateAndTime;
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


	public Date getExpectedTime() {
		return expectedTime;
	}


	public void setExpectedTime(Date expectedTime) {
		this.expectedTime = expectedTime;
	}


	public Date getDateOfScheduling() {
		return dateOfScheduling;
	}


	public void setDateOfScheduling(Date dateOfScheduling) {
		this.dateOfScheduling = dateOfScheduling;
	}


	public Integer getVisitNumber() {
		return visitNumber;
	}


	public void setVisitNumber(Integer visitNumber) {
		this.visitNumber = visitNumber;
	}


	public String getIsVisitDone() {
		return isVisitDone;
	}


	public void setIsVisitDone(String isVisitDone) {
		this.isVisitDone = isVisitDone;
	}


	public String getVisitingForDescription() {
		return visitingForDescription;
	}


	public void setVisitingForDescription(String visitingForDescription) {
		this.visitingForDescription = visitingForDescription;
	}


	public Integer getScheduledNumber() {
		return scheduledNumber;
	}


	public void setScheduledNumber(Integer scheduledNumber) {
		this.scheduledNumber = scheduledNumber;
	}


	public AppointmentType getTypeOfAppointment() {
		return typeOfAppointment;
	}


	public void setTypeOfAppointment(AppointmentType typeOfAppointment) {
		this.typeOfAppointment = typeOfAppointment;
	}

	public static Comparator<CustomerAppointmentDomain> sortByScheduleNumber = new Comparator<CustomerAppointmentDomain>()
	{ 
	   
		@Override
		public int compare(CustomerAppointmentDomain o1, CustomerAppointmentDomain o2) {
			// TODO Auto-generated method stub
			if(o1!=null && o2!=null && o1.getScheduledNumber()!=null && o2.getScheduledNumber()!=null) {
			return o1.getScheduledNumber().compareTo(o2.getScheduledNumber());
			}
			else {
				return 0;
			}
		} 
	}; 
	
}
