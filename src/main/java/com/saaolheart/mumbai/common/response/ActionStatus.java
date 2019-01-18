package com.saaolheart.mumbai.common.response;

public enum ActionStatus {
	
	SUCCESS("Success"),
	VALIDATIONERROR("Validation Error"),
	FAILED("FAILED");
	
	public String status;
	ActionStatus(String val) {
		this.status = val;
	}
	public String getStatus() {
		return status;
	}
	
	

}
