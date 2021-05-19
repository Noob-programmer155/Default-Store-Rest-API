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
	private BigDecimal amountMoney = new BigDecimal(0.0);
	private BigDecimal purchase = new BigDecimal(0.0);
	private BigDecimal income = new BigDecimal(0.0);
	
	private HashMap<LocalDate,Finance> dataFinance = new HashMap<>();
	
	public void income(BigDecimal in) {
		income.add(in);
	}
	
	public void expenses(BigDecimal expense) {
		purchase.add(expense);
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
		income.multiply(BigDecimal.ZERO);
		purchase.multiply(BigDecimal.ZERO);
		return finance;
	}
	
	public BigDecimal saveAmountMoney() {
		return amountMoney.add(income.add(purchase.negate()));
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
}
