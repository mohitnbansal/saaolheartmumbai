package com.saaolheart.mumbai.customer;

public enum VisitngFor {
	PRODUCT("Product"),
	ECP("ECP"),
	BCA("BCA");

	public String visit;
	VisitngFor(String val) {
		visit = val;
		
		
	}
	
	public String getVisit() {
		return visit;
	}
}
