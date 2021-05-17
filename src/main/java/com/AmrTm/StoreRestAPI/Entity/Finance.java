package com.AmrTm.StoreRestAPI.Entity;

import java.math.BigDecimal;

public class Finance {
	private BigDecimal amountMoney;
	private BigDecimal purchase;
	private BigDecimal income;
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
