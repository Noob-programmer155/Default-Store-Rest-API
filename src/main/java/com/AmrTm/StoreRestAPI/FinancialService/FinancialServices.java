package com.AmrTm.StoreRestAPI.FinancialService;

import java.math.BigDecimal;
import java.util.List;

import javax.money.Monetary;
import javax.money.MonetaryAmount;

import org.springframework.stereotype.Service;

@Service
public class FinancialServices {
	private BigDecimal amountMoney = new BigDecimal(0.0);
	private BigDecimal purchase = new BigDecimal(0.0);
	private BigDecimal income = new BigDecimal(0.0);
	
	private List<BigDecimal> dataFinance;
	
	public void income(double in) {
		income.add(BigDecimal.valueOf(in));
	}
	
	public void expense(double expense) {
		purchase.add(BigDecimal.valueOf(expense));
	}
	
	public String profitNLoss() {
		MonetaryAmount amount;
		if(income.doubleValue() > purchase.doubleValue()) {
			amount = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(income.add(purchase.negate()).doubleValue()).create();
			return "profit: "+amount.toString();}
		else if(income.doubleValue() < purchase.doubleValue()) {
			amount = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(purchase.add(income.negate()).doubleValue()).create();
			return "loss: "+amount.toString();}
		else
			return "no income";
	}
	
}
