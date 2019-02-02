package com.saaolheart.mumbai.invoice;

public enum InvoiceStatuses {
	
	NOTPAID("Total Payment Pending"),
	PARTIALLYPAID("Partially Pending"),
	PAYMENTDONE("Payement Conpleted"),
	CANCELLED("Invoice Cancelled");
	
	public  String invoiceStatuses;
	
	
	public String getInvoiceStatuses() {
		return invoiceStatuses;
	}


	public void setInvoiceStatuses(String invoiceStatuses) {
		this.invoiceStatuses = invoiceStatuses;
	}


	private InvoiceStatuses(String status) {
		this.invoiceStatuses = status;
		// TODO Auto-generated constructor stub
	}

}
