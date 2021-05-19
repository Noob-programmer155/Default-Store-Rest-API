package com.AmrTm.StoreRestAPI.MainController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AmrTm.StoreRestAPI.Entity.Finance;
import com.AmrTm.StoreRestAPI.Entity.Item;
import com.AmrTm.StoreRestAPI.FinancialService.FinancialServices;
import com.AmrTm.StoreRestAPI.LogDataBase.Log;
import com.AmrTm.StoreRestAPI.LogDataBase.LogData;

@RestController
@RequestMapping("/financial")
public class FinancialRest {
	private UserRest userRest;
	private ItemRest itemRest;
	private FinancialServices financialServices;
	private LogData logData;
	private Logger log = LogManager.getLogger(FinancialRest.class);
	
	public FinancialRest(UserRest userRest, ItemRest itemRest,
			FinancialServices financialServices,LogData logData) {
		super();
		this.userRest = userRest;
		this.itemRest = itemRest;
		this.financialServices = financialServices;
		this.logData = logData;
	}
	
	@PostMapping("/save/{name}/{mount}")
	public String sales(@RequestBody Item item, @PathVariable String name, @PathVariable int mount, 
			@RequestAttribute(required=false) String id_user, @RequestAttribute String type){
		financialServices.income(item.getCost().multiply(BigDecimal.valueOf(mount)));
		if(id_user != null)
			userRest.modifyUser(item, id_user);
		else
			log.info("anonymous customer item: "+item.getId()+" buyed");
		itemRest.deleteItemSubItem(name, item.getId(), type, mount);
		return "income has been saved";
	}
	
	@PostMapping("/save/{name}/{mount}")
	public String expense(@RequestBody Item item, @PathVariable String name, @PathVariable int mount, 
			@RequestAttribute String type) {
		financialServices.expenses(item.getCost().multiply(BigDecimal.valueOf(mount)));
		itemRest.addItemSubItem(item, name, type, mount);
		return "expense has been saved";
	}
	
	// using report sales minimal 1 day
	@PostMapping("/report")
	public ResponseEntity<Finance> reportFinance(){
		Finance finance = financialServices.report();
		return ResponseEntity.ok(finance);
	}
	
	@GetMapping("/logs/{date}")
	public List<Log> getLogs(@PathVariable String date){
		return logData.getLog(LocalDate.parse(date));
	}
}
