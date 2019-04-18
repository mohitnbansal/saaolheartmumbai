package com.saaolheart.mumbai.treatment.treatmentplan;

import java.io.Serializable;
import java.time.Duration;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="TREATMENT_PLAN_DETAILS")
public class TreatmentPlanDetailDomain implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	
	@Column(name="DURATION")
	private Duration duration;

	
	@Column(name="COMPLAINTS")
	private String complaints;
	
	@Column(name="IS_TREATMENT_DONE")
	private String isTreatmentDone;


	@Column(name="MACHINE_NO")
	private Integer machineNo;
	
	@Column(name="TREATMENT_PLAN_ID")
	private Long treatmentPlanId;
	
	
	@Column(name="VISIT_NUMBER")
	private Integer visitNumber;
/**
 * Consider as Start date and time
 */
	@Column(name="SCHEDULED_DATE")
	private Date treatmentScheduledDate;
	
	/**
	 * Consider as END date and time
	 */
	
	@Column(name="TREATMENT_DATE")
	private Date treatmentDate;
	
	@Column(name="TREATMENT_TYPE")
	private String treatmentType;
	
	@Column(name="DURATION_TILL_NOW")
	private Duration durationUpTillNow;
	
	
	

	
	public Duration getDurationUpTillNow() {
		return durationUpTillNow;
	}

	public void setDurationUpTillNow(Duration durationUpTillNow) {
		this.durationUpTillNow = durationUpTillNow;
	}

	public Integer getVisitNumber() {
		return visitNumber;
	}

	public void setVisitNumber(Integer visitNumber) {
		this.visitNumber = visitNumber;
	}


	public String getTreatmentType() {
		return treatmentType;
	}

	public void setTreatmentType(String treatmentType) {
		this.treatmentType = treatmentType;
	}

	@Transient
	private TreatmentPlanDomain treatmentDomain;
	
	

	
	public TreatmentPlanDomain getTreatmentDomain() {
		return treatmentDomain;
	}

	public void setTreatmentDomain(TreatmentPlanDomain treatmentDomain) {
		this.treatmentDomain = treatmentDomain;
	}

	public Date getTreatmentScheduledDate() {
		return treatmentScheduledDate;
	}

	public void setTreatmentScheduledDate(Date treatmentScheduledDate) {
		this.treatmentScheduledDate = treatmentScheduledDate;
	}

	public String getIsTreatmentDone() {
		return isTreatmentDone;
	}

	public void setIsTreatmentDone(String isTreatmentDone) {
		this.isTreatmentDone = isTreatmentDone;
	}

	public Integer getMachineNo() {
		return machineNo;
	}

	public void setMachineNo(Integer machineNo) {
		this.machineNo = machineNo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTreatmentDate() {
		return treatmentDate;
	}

	public void setTreatmentDate(Date treatmentDate) {
		this.treatmentDate = treatmentDate;
	}



	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}



	public String getComplaints() {
		return complaints;
	}

	public void setComplaints(String complaints) {
		this.complaints = complaints;
	}

	public Long getTreatmentPlanId() {
		return treatmentPlanId;
	}

	public void setTreatmentPlanId(Long treatmentPlanId) {
		this.treatmentPlanId = treatmentPlanId;
	}
	
	

}
