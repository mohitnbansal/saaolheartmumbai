package com.saaolheart.mumbai.masters.treatment;

import java.io.Serializable;
import java.time.Duration;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TREATMENT_TYPE_MASTER")
public class TreatmentTypeMasterDomain implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Long id;
	
	@Column(name="TREATMENT_NAME")
	private String treatmentName;
	
	@Column(name="TOTAL_NO_OF_SITTINGS")
	private Integer totalNoOfSittings;
	
	@Column(name="TOTAL_COST")
	private Double totalCost;

	
	@Column(name="TOTAL_HOURS")
	private Duration totalHours;
	
	
	


	public Duration getTotalHours() {
		return totalHours;
	}

	public void setTotalHours(Duration totalHours) {
		this.totalHours = totalHours;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTreatmentName() {
		return treatmentName;
	}

	public void setTreatmentName(String treatmentName) {
		this.treatmentName = treatmentName;
	}

	public Integer getTotalNoOfSittings() {
		return totalNoOfSittings;
	}

	public void setTotalNoOfSittings(Integer totalNoOfSittings) {
		this.totalNoOfSittings = totalNoOfSittings;
	}

	public Double getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Double totalCost) {
		this.totalCost = totalCost;
	}
	
	
	

}
