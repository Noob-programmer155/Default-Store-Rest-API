package com.AmrTm.StoreRestAPI;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.AmrTm.StoreRestAPI.Configuration.ItemConfiguration;
import com.AmrTm.StoreRestAPI.Configuration.UserConfiguration;
import com.AmrTm.StoreRestAPI.Entity.Item;
import com.AmrTm.StoreRestAPI.Entity.User;
import com.AmrTm.StoreRestAPI.Service.FinancialServices;
import com.AmrTm.StoreRestAPI.Service.ItemType;
import com.AmrTm.StoreRestAPI.Service.SubItems;

/**
 * @author Amar
 * */
@SpringBootApplication
public class StoreManagementRestApiApplication implements CommandLineRunner{
	
	@Autowired
	private ItemConfiguration itemConfiguration;
	@Autowired
	private UserConfiguration userConfiguration;
	@Autowired
	private FinancialServices financialServices;

	public static void main(String[] args) {
		SpringApplication.run(StoreManagementRestApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		itemConfiguration.addSubItems(new SubItems("pasta gigi",ItemType.CONSUMPTIONITEMS));
		itemConfiguration.addItemSubItems(itemConfiguration.getSubItem("pasta gigi",ItemType.CONSUMPTIONITEMS), new Item("Pepsodent",BigDecimal.valueOf(3000)), 30);
		itemConfiguration.addItemSubItems(itemConfiguration.getSubItem("pasta gigi",ItemType.CONSUMPTIONITEMS), new Item("Ciptadent",BigDecimal.valueOf(2800)), 50);
		itemConfiguration.addItemSubItems(itemConfiguration.getSubItem("pasta gigi",ItemType.CONSUMPTIONITEMS), new Item("Enzim",BigDecimal.valueOf(3200)), 30);
		itemConfiguration.addSubItems(new SubItems("Blender",ItemType.NONCONSUMPTIONITEMS));
		itemConfiguration.addItemSubItems(itemConfiguration.getSubItem("Blender",ItemType.NONCONSUMPTIONITEMS), new Item("Sharp",BigDecimal.valueOf(95000)), 10);
		itemConfiguration.addItemSubItems(itemConfiguration.getSubItem("Blender",ItemType.NONCONSUMPTIONITEMS), new Item("Panasonic",BigDecimal.valueOf(100000)), 10);
		itemConfiguration.addItemSubItems(itemConfiguration.getSubItem("Blender",ItemType.NONCONSUMPTIONITEMS), new Item("Miyako",BigDecimal.valueOf(110000)), 10);
		
		userConfiguration.save(new User("Pandu"));
		
		financialServices.setAmountMoney(BigDecimal.valueOf(5500000));
	}
}
