package com.saaolheart.mumbai.common.constants;

public enum StatusOfTreatement {
	
	DRAFT("Draft"),INPROGRESS("InProgress"),TERMINATED("Terminated"),CLOSED("Closed");
	
	private String statusOfTreatment;
	
	private StatusOfTreatement(String value) {
		this.statusOfTreatment = value;
		// TODO Auto-generated constructor stub
	}

	public String getStatusOfTreatment() {
		return statusOfTreatment;
	}
	
	

}
