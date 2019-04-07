package com.saaolheart.mumbai.store.stock;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.saaolheart.mumbai.security.domain.User;

@Entity
@Table(name="STOCK_HSTRY_DETAILS")
public class StockHistoryDetailsDomain implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column(name="QTY_UPDATED")
	private Long qtyUpdated;
	
	@Column(name="STOCK_RATE")
	private Double stockRate;
	
	@Column(name="STOCK_AVAILABLE")
	private Long availableStock;
	
	@Column(name="STOCK_VALUE")
	private Double stockValue;
	
	@Column(name="REASON_FOR_UPDATE")
	private String reasonForUpdate;
	
	@Column(name="IS_MANUAL_UPDATE")
	private String isManualUpdate;
	
	@Column(name="UPDATED_ON")
	private Date updatedOn;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@ManyToOne
	@JoinColumn(name="LAST_UPDATED_BY",referencedColumnName="username",insertable=false,updatable=false)
	private User lastUpdatedByUser;
	
	@Column(name="STOCK_ID")
	private Long stockId;
	
	
	
	
	public Long getStockId() {
		return stockId;
	}

	public void setStockId(Long stockId) {
		this.stockId = stockId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQtyUpdated() {
		return qtyUpdated;
	}

	public void setQtyUpdated(Long qtyUpdated) {
		this.qtyUpdated = qtyUpdated;
	}

	public Double getStockRate() {
		return stockRate;
	}

	public void setStockRate(Double stockRate) {
		this.stockRate = stockRate;
	}

	public Long getAvailableStock() {
		return availableStock;
	}

	public void setAvailableStock(Long availableStock) {
		this.availableStock = availableStock;
	}

	public Double getStockValue() {
		return stockValue;
	}

	public void setStockValue(Double stockValue) {
		this.stockValue = stockValue;
	}

	public String getReasonForUpdate() {
		return reasonForUpdate;
	}

	public void setReasonForUpdate(String reasonForUpdate) {
		this.reasonForUpdate = reasonForUpdate;
	}

	public String getIsManualUpdate() {
		return isManualUpdate;
	}

	public void setIsManualUpdate(String isManualUpdate) {
		this.isManualUpdate = isManualUpdate;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	public User getLastUpdatedByUser() {
		return lastUpdatedByUser;
	}

	public void setLastUpdatedByUser(User lastUpdatedByUser) {
		this.lastUpdatedByUser = lastUpdatedByUser;
	}
	
	
	


}
