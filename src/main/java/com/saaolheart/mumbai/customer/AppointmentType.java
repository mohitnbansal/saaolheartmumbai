package com.saaolheart.mumbai.customer;

public enum AppointmentType {
	
	INHOUSE("In House"),
	DR_APPOINTMENT("Dr Appointment"),
	TREATMENT_ECP("Treatment ECP"),
	TREATMENT_BCP("Treatment BCP");
	
	AppointmentType(String str) {
		this.appointmentString = str;
		
	}
	
	public String appointmentString;

	public String getAppointmentString() {
		return appointmentString;
	}
	
	public static AppointmentType getAppointmentType(String app) {
		if("In House".equalsIgnoreCase(app)) {
			return AppointmentType.INHOUSE;
		}else if("Dr Appointment".equalsIgnoreCase(app)) {
			return AppointmentType.DR_APPOINTMENT;
		}else if("Treatment ECP".equalsIgnoreCase(app)) {
			return AppointmentType.TREATMENT_ECP;
		}else {
			return AppointmentType.TREATMENT_BCP;
		}
	}
	

}
