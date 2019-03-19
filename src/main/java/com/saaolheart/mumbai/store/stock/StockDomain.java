package com.saaolheart.mumbai.store.stock;


import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.saaolheart.mumbai.security.domain.User;

@Entity
@Table(name="STOCK") 
public class StockDomain implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Long id;
	
	@Column(name="STOCK_CATEGORY_ID")
	private Long stockCategoryid;
	
	@OneToOne
	@JoinColumn(referencedColumnName="ID",name="STOCK_CATEGORY_ID",insertable=false,updatable=false)
	private StockCategoryDomain stockCategoryDomain;
	
	@Column(name="STOCK_NAME")
	private String stockName;
	
	@Column(name="STOCK_DESCP")
	private String stockDescription;
	
	@OneToMany(cascade= {CascadeType.ALL},orphanRemoval=true)
	@JoinColumn(referencedColumnName="ID",name="STOCK_ID")
	@Fetch(value=FetchMode.SELECT)
	private List<StockHistoryDetailsDomain> stockHistoryDetailsList;
	
	
	@Column(name="QTY_AVAILABLE")
	private Long qtyOfStockAvailable;
	
	@Column(name="CURRENT_RATE")
	private Double currentRateOfStock;
	
	@Column(name="CURRENT_VALUE")
	private Double curentStockValue;
	
	@Column(name="ADDED_ON")
	private Date addedOn;
	
	@Column(name="LAST_UPDATED_ON")
	private Date lastUpdatedOn;
	
	@Column(name="LAST_UPDATED_BY")
	private String lastUpdatedBy;
	
	@ManyToOne
	@JoinColumn(name="LAST_UPDATED_BY",referencedColumnName="username",insertable=false,updatable=false)
	private User lastUpdatedByUser;
	
	@JsonIgnore
	@Transient
	private String stockCategory;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getStockCategoryid() {
		return stockCategoryid;
	}

	public void setStockCategoryid(Long stockCategoryid) {
		this.stockCategoryid = stockCategoryid;
	}

	public StockCategoryDomain getStockCategoryDomain() {
		return stockCategoryDomain;
	}

	public void setStockCategoryDomain(StockCategoryDomain stockCategoryDomain) {
		this.stockCategoryDomain = stockCategoryDomain;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getStockDescription() {
		return stockDescription;
	}

	public void setStockDescription(String stockDescription) {
		this.stockDescription = stockDescription;
	}

	public List<StockHistoryDetailsDomain> getStockHistoryDetailsList() {
		return stockHistoryDetailsList;
	}

	public void setStockHistoryDetailsList(List<StockHistoryDetailsDomain> stockHistoryDetailsList) {
		this.stockHistoryDetailsList = stockHistoryDetailsList;
	}

	public Long getQtyOfStockAvailable() {
		return qtyOfStockAvailable;
	}

	public void setQtyOfStockAvailable(Long qtyOfStockAvailable) {
		this.qtyOfStockAvailable = qtyOfStockAvailable;
	}

	

	public Double getCurrentRateOfStock() {
		return currentRateOfStock;
	}

	public void setCurrentRateOfStock(Double currentRateOfStock) {
		this.currentRateOfStock = currentRateOfStock;
	}

	public Double getCurentStockValue() {
		return curentStockValue;
	}

	public void setCurentStockValue(Double curentStockValue) {
		this.curentStockValue = curentStockValue;
	}

	public Date getAddedOn() {
		return addedOn;
	}

	public void setAddedOn(Date addedOn) {
		this.addedOn = addedOn;
	}

	public Date getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
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

	public String getStockCategory() {
		setStockCategory(this.stockCategoryDomain.getCategoryName()); 
		return stockCategory;
	}

	public void setStockCategory(String stockCategory) {
		this.stockCategory = stockCategory;
	}
	
	

	
}
