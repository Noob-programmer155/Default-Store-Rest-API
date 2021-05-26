package com.AmrTm.StoreRestAPI.Entity;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Object entity for finance
 * @author Amar
 * */
@ApiModel(description="Finance entity model")
public class Finance {
	@ApiModelProperty(notes="mount of all cash in the store")
	private BigDecimal amountMoney;
	@ApiModelProperty(notes="mount of purchase item cash for store")
	private BigDecimal purchase;
	@ApiModelProperty(notes="mount of cash in come to the store")
	private BigDecimal income;
	@ApiModelProperty(notes="information about financial in this store")
	private String information;
	
	public Finance() {
		super();
	}
	
	public Finance(BigDecimal amountMoney, BigDecimal purchase, BigDecimal income, String information) {
		super();
		this.amountMoney = amountMoney;
		this.purchase = purchase;
		this.income = income;
		this.information = information;
	}
	
	public void setAmountMoney(BigDecimal amountMoney) {
		this.amountMoney = amountMoney;
	}
	public void setPurchase(BigDecimal purchase) {
		this.purchase = purchase;
	}
	public void setIncome(BigDecimal income) {
		this.income = income;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public BigDecimal getAmountMoney() {
		return amountMoney;
	}
	public BigDecimal getPurchase() {
		return purchase;
	}
	public BigDecimal getIncome() {
		return income;
	}
	public String getInformation() {
		return information;
	}
}
