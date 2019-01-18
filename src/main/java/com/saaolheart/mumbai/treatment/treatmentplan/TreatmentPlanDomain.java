package com.saaolheart.mumbai.treatment.treatmentplan;

import java.io.Serializable;
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

import com.saaolheart.mumbai.masters.treatment.TreatmentTypeMasterDomain;

@Entity
@Table(name="TREATMENT_PLAN")
public class TreatmentPlanDomain implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="TREATMENT_TYPE_MASTER_ID")
	private Long treatmentTypeMasterId;
	
	
	@ManyToOne
	@JoinColumn(name="TREATMENT_TYPE_MASTER_ID",referencedColumnName="ID",insertable=false,updatable=false)
	private TreatmentTypeMasterDomain treatmentMaster;
	
	@Column(name="TREATMENT_STATUS")
	private String treatmentStatus;

	@OneToMany(cascade= {CascadeType.ALL},orphanRemoval=true)
	@JoinColumn(name="TREATMENT_PLAN_ID",referencedColumnName="ID")
	private List<TreatmentPlanDetailDomain> treatmentPlanDetailsList;
	

	@Column(name="CUSTOMER_ID")
	private Long customerId;
	
	
	
	
	public Long getTreatmentTypeMasterId() {
		return treatmentTypeMasterId;
	}

	public void setTreatmentTypeMasterId(Long treatmentTypeMasterId) {
		this.treatmentTypeMasterId = treatmentTypeMasterId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public List<TreatmentPlanDetailDomain> getTreatmentPlanDetailsList() {
		return treatmentPlanDetailsList;
	}

	public void setTreatmentPlanDetailsList(List<TreatmentPlanDetailDomain> treatmentPlanDetailsList) {
		this.treatmentPlanDetailsList = treatmentPlanDetailsList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public TreatmentTypeMasterDomain getTreatmentMaster() {
		return treatmentMaster;
	}

	public void setTreatmentMaster(TreatmentTypeMasterDomain treatmentMaster) {
		this.treatmentMaster = treatmentMaster;
	}

	public String getTreatmentStatus() {
		return treatmentStatus;
	}

	public void setTreatmentStatus(String treatmentStatus) {
		this.treatmentStatus = treatmentStatus;
	}
	
	
	
}
