package com.AmrTm.StoreRestAPI;

<<<<<<< HEAD
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
=======
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.AmrTm.StoreRestAPI.Entity.Item;
import com.AmrTm.StoreRestAPI.Entity.User;
import com.AmrTm.StoreRestAPI.FinancialService.FinancialServices;
import com.AmrTm.StoreRestAPI.ItemService.ItemConfiguration;
import com.AmrTm.StoreRestAPI.ItemService.ItemType;
import com.AmrTm.StoreRestAPI.ItemService.SubItems;
import com.AmrTm.StoreRestAPI.UserService.UserConfiguration;
>>>>>>> a801fe1aca50ff6ebbe8fbba6a77bf8afe0ec7cb
/**
 * @author Amar
 * */
@SpringBootApplication
<<<<<<< HEAD
public class StoreManagementRestApiApplication {
=======
public class StoreManagementRestApiApplication implements CommandLineRunner{
	
	@Autowired
	private ItemConfiguration itemConfiguration;
	@Autowired
	private UserConfiguration userConfiguration;
	@Autowired
	private FinancialServices financialServices;
>>>>>>> a801fe1aca50ff6ebbe8fbba6a77bf8afe0ec7cb

	public static void main(String[] args) {
		SpringApplication.run(StoreManagementRestApiApplication.class, args);
	}

<<<<<<< HEAD
=======
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

>>>>>>> a801fe1aca50ff6ebbe8fbba6a77bf8afe0ec7cb
}
