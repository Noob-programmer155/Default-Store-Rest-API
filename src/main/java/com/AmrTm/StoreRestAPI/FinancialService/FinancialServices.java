package com.AmrTm.StoreRestAPI.FinancialService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.AmrTm.StoreRestAPI.Entity.Finance;
import com.AmrTm.StoreRestAPI.LogDataBase.LogData;

@Service
public class FinancialServices {
	@Autowired
	private LogData logData;
	
	private static Logger log = LogManager.getLogger(FinancialServices.class);
	private BigDecimal amountMoney = BigDecimal.ZERO;
	private BigDecimal purchase = BigDecimal.ZERO;
	private BigDecimal income = BigDecimal.ZERO;
	
	private HashMap<LocalDate,Finance> dataFinance = new HashMap<>();
	
	public void income(BigDecimal in) {
		income = income.add(in);
	}
	
	public void expenses(BigDecimal expense) {
		purchase = purchase.add(expense);
	}
	
	public String profitNLoss() {
		MonetaryAmount amount;
		if(income.doubleValue() > purchase.doubleValue()) {
			amount = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(income.add(purchase.negate())).create();
			return "profit: "+amount.toString();}
		else if(income.doubleValue() < purchase.doubleValue()) {
			amount = Monetary.getDefaultAmountFactory().setCurrency("USD").setNumber(purchase.add(income.negate())).create();
			return "loss: "+amount.toString();}
		else
			return "no income";
	}
	
	public Finance report() {
		BigDecimal mount = saveAmountMoney();
		Finance finance = new Finance(mount,purchase,income,profitNLoss());
		dataFinance.put(LocalDate.now(), finance);
		logData.saveLog("Data Financial: income=> "+currencyConverter(income, "USD")+" | expense=> "+currencyConverter(purchase, "USD")+" | information=== "+profitNLoss());
		log.info("Data Financial for ["+LocalDate.now()+"] success to saved | information=== "+profitNLoss());
		income = income.multiply(BigDecimal.ZERO);
		purchase = purchase.multiply(BigDecimal.ZERO);
		return finance;
	}
	
	public BigDecimal saveAmountMoney() {
		return amountMoney = amountMoney.add(income.add(purchase.negate()));
	}
	
	public BigDecimal getAmountMoney() {
		return amountMoney;
	}

	public String currencyConverter(BigDecimal Money, String currency) {
		return Monetary.getDefaultAmountFactory().setCurrency(currency).setNumber(Money).create().toString();
	}

	public HashMap<LocalDate, Finance> getDataFinance() {
		return dataFinance;
	}

	public void setDataFinance(HashMap<LocalDate, Finance> dataFinance) {
		this.dataFinance = dataFinance;
	}

	public void setAmountMoney(BigDecimal amountMoney) {
		this.amountMoney = amountMoney;
	}
}
