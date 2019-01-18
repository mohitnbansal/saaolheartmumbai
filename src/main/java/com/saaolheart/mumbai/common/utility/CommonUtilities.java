package com.saaolheart.mumbai.common.utility;

import java.util.Calendar;
import java.util.Date;

public class CommonUtilities {
	
	public static String getRequestReference(Long id,Date creationDate) {
		long timestamp = creationDate.getTime();
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(timestamp);
		 int year = cal.get(Calendar.YEAR);
			String autoNoAppend = String.format("%08d", id);
	 String customerReference= "CUST/"+year+"/"+autoNoAppend;
	return customerReference;
	}

}
