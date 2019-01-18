package com.saaolheart.mumbai.invoice;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.saaolheart.mumbai.customer.CustomerDetail;
import com.saaolheart.mumbai.masters.invoice.InvoiceTypeMaster;
import com.saaolheart.mumbai.security.domain.User;

@Entity
@Table(name="INVOICE")
public class InvoiceDomain  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;
	
	@OneToMany(cascade= {CascadeType.ALL})
	@JoinColumn(name="INVOICE_ID",referencedColumnName="ID")
	private List<InvoiceRecieptDetailDomain> invoiceReciptList;
	
	@Column(name="INVOICE_TYPE_ID")
	private Long invoiceTypeId;
	
	@ManyToOne
	@JoinColumn(referencedColumnName="ID",name="INVOICE_TYPE_ID",insertable=false,updatable=false)
	private InvoiceTypeMaster invoiceType;
	
	@Column(name="TOTAL_INVOICE_AMT")
	private Double totalInvoiceAmt;
	
	@Column(name="BALANCE_AMT")
	private Double balanceAmt;
	
	@Column(name="DISCOUNT_AMT")
	private Double discountAmt;
	
	@Column(name="INVOICE_STATUS")
	private String invoiceStatus;
	
	@Column(name="GENERATED_BY")
	private Long generetedBy;
	
	@OneToOne
	@JoinColumn(name="GENERATED_BY",referencedColumnName="ID",insertable=false,updatable=false)
	private User generatedBy;


	public Long getInvoiceTypeId() {
		return invoiceTypeId;
	}

	public void setInvoiceTypeId(Long invoiceTypeId) {
		this.invoiceTypeId = invoiceTypeId;
	}

	public Long getGeneretedBy() {
		return generetedBy;
	}

	public void setGeneretedBy(Long generetedBy) {
		this.generetedBy = generetedBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public List<InvoiceRecieptDetailDomain> getInvoiceReciptList() {
		return invoiceReciptList;
	}

	public void setInvoiceReciptList(List<InvoiceRecieptDetailDomain> invoiceReciptList) {
		this.invoiceReciptList = invoiceReciptList;
	}

	public InvoiceTypeMaster getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(InvoiceTypeMaster invoiceType) {
		this.invoiceType = invoiceType;
	}

	public Double getTotalInvoiceAmt() {
		return totalInvoiceAmt;
	}

	public void setTotalInvoiceAmt(Double totalInvoiceAmt) {
		this.totalInvoiceAmt = totalInvoiceAmt;
	}

	public Double getBalanceAmt() {
		return balanceAmt;
	}

	public void setBalanceAmt(Double balanceAmt) {
		this.balanceAmt = balanceAmt;
	}

	public Double getDiscountAmt() {
		return discountAmt;
	}

	public void setDiscountAmt(Double discountAmt) {
		this.discountAmt = discountAmt;
	}

	public String getInvoiceStatus() {
		return invoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public User getGeneratedBy() {
		return generatedBy;
	}

	public void setGeneratedBy(User generatedBy) {
		this.generatedBy = generatedBy;
	}
	
	
	
	
}
