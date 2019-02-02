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
	
	@Column(name="TREATMENT_DATE")
	private Date treatmentDate;
	
	@Column(name="DURATION")
	private String duration;
	
	@Column(name="BEG_BP")
	private String begBp;
	
	@Column(name="BEG_HP")
	private String begHp;
	
	@Column(name="END_BP")
	private String endBp;
	
	@Column(name="END_HP")
	private String endHp;
	
	@Column(name="COMPLAINTS")
	private String complaints;
	
	@Column(name="TREATMENT_PLAN_ID")
	private Long treatmentPlanId;

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

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getBegBp() {
		return begBp;
	}	

	public void setBegBp(String begBp) {
		this.begBp = begBp;
	}

	public String getBegHp() {
		return begHp;
	}

	public void setBegHp(String begHp) {
		this.begHp = begHp;
	}

	public String getEndBp() {
		return endBp;
	}

	public void setEndBp(String endBp) {
		this.endBp = endBp;
	}

	public String getEndHp() {
		return endHp;
	}

	public void setEndHp(String endHp) {
		this.endHp = endHp;
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
