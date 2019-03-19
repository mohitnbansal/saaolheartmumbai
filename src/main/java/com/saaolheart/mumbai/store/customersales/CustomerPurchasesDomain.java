package com.saaolheart.mumbai.store.customersales;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.saaolheart.mumbai.store.stock.StockDomain;


@Entity
@Table(name="CUSTOMER_PURCHASES")
public class CustomerPurchasesDomain implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column(name="STOCK_ID")
	private Long stockDomainId;
	
	@OneToOne
	@JoinColumn(referencedColumnName="ID",name="STOCK_ID",insertable=false,updatable=false)
	private StockDomain stockDomain;
	
	@Column(name="QTY_PURCHASED")
	private Long quantityPurchased;
	
	
	@Column(name="RATE")
	private Double rateOfStock;
	
	
	@Column(name="PRICE")
	private Double price;
	
	
	@Column(name="IS_CANCELLED")
	private String isCancelled;
	
	@Column(name="CUSTOMER_SALES_ID")
	private Long customerSaledId;
	
	
	@Transient
	private Long customerId;
	
	@Transient
	private Double totalInvoiceAmt;
	
	

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Double getTotalInvoiceAmt() {
		return totalInvoiceAmt;
	}

	public void setTotalInvoiceAmt(Double totalInvoiceAmt) {
		this.totalInvoiceAmt = totalInvoiceAmt;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStockDomainId() {
		return stockDomainId;
	}

	public void setStockDomainId(Long stockDomainId) {
		this.stockDomainId = stockDomainId;
	}

	public StockDomain getStockDomain() {
		return stockDomain;
	}

	public void setStockDomain(StockDomain stockDomain) {
		this.stockDomain = stockDomain;
	}

	public Long getQuantityPurchased() {
		return quantityPurchased;
	}

	public void setQuantityPurchased(Long quantityPurchased) {
		this.quantityPurchased = quantityPurchased;
	}

	public Double getRateOfStock() {
		return rateOfStock;
	}

	public void setRateOfStock(Double rateOfStock) {
		this.rateOfStock = rateOfStock;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getIsCancelled() {
		return isCancelled;
	}

	public void setIsCancelled(String isCancelled) {
		this.isCancelled = isCancelled;
	}

	public Long getCustomerSaledId() {
		return customerSaledId;
	}

	public void setCustomerSaledId(Long customerSaledId) {
		this.customerSaledId = customerSaledId;
	}
	
	
	

}
