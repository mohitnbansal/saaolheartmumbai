package com.saaolheart.mumbai.customer;


import java.io.Serializable;
import java.time.Duration;
import java.util.Comparator;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.saaolheart.mumbai.treatment.treatmentplan.TreatmentPlanDetailDomain;
import com.saaolheart.mumbai.treatment.treatmentplan.TreatmentPlanDomain;


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
	
	@Transient
	private String dateOfSchedulingForDisplay;
	

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
	
	@Column(name="TREATMENT_PLAN_ID")
	private Long treatmentPlanId;
	
	@Column(name="TREATMENT_PLAN_DETAIL_ID")
	private Long treatmentDetailPlanId;
	
	@Column(name="TIME_IN_DURATION")
	@Convert(converter = DurationToStringConvertor.class)
	private Duration timeInDuration;
	
	@Transient
	private String customerName;
	
	
	@Transient
	private String typeOfAppointmentString;

	@Transient
	@Convert(converter = DurationToStringConvertor.class)
	private Duration durationOfTreatment;
	
	@Column(name="MACHINE_NO")
	private Integer machineNo;
	
@Transient
private Double duration;

@Transient
private Date start;

@Transient
private Date end;

@Transient
private TreatmentPlanDetailDomain treatmentDetailDomain;











public TreatmentPlanDetailDomain getTreatmentDetailDomain() {
	return treatmentDetailDomain;
}




public void setTreatmentDetailDomain(TreatmentPlanDetailDomain treatmentDetailDomain) {
	this.treatmentDetailDomain = treatmentDetailDomain;
}




public Duration getTimeInDuration() {
	return timeInDuration;
}




public void setTimeInDuration(Duration timeInDuration) {
	this.timeInDuration = timeInDuration;
}












public Double getDuration() {
	return duration;
}




public void setDuration(Double duration) {
	this.duration = duration;
}




public Date getStart() {
	return start;
}


public void setStart(Date start) {
	this.start = start;
}


public Date getEnd() {
	return end;
}


public void setEnd(Date end) {
	this.end = end;
}


	public String getDateOfSchedulingForDisplay() {
		return dateOfSchedulingForDisplay;
	}


	public void setDateOfSchedulingForDisplay(String dateOfSchedulingForDisplay) {
		this.dateOfSchedulingForDisplay = dateOfSchedulingForDisplay;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public Long getTreatmentDetailPlanId() {
		return treatmentDetailPlanId;
	}


	public void setTreatmentDetailPlanId(Long treatmentDetailPlanId) {
		this.treatmentDetailPlanId = treatmentDetailPlanId;
	}


	public Long getTreatmentPlanId() {
		return treatmentPlanId;
	}


	public void setTreatmentPlanId(Long treatmentPlanId) {
		this.treatmentPlanId = treatmentPlanId;
	}




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
