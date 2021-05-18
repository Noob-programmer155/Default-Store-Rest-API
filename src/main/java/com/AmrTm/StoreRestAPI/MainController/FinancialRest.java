package com.AmrTm.StoreRestAPI.MainController;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AmrTm.StoreRestAPI.FinancialService.FinancialServices;
import com.AmrTm.StoreRestAPI.ItemService.ItemConfiguration;
import com.AmrTm.StoreRestAPI.UserService.UserConfiguration;

@RestController
@RequestMapping("/financial")
public class FinancialRest {
	private UserConfiguration userConfiguration;
	private ItemConfiguration itemConfiguration;
	private FinancialServices financialServices;
	
	public FinancialRest(UserConfiguration userConfiguration, ItemConfiguration itemConfiguration,
			FinancialServices financialServices) {
		super();
		this.userConfiguration = userConfiguration;
		this.itemConfiguration = itemConfiguration;
		this.financialServices = financialServices;
	}
	
	
}
