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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.AmrTm.StoreRestAPI.Entity.Finance;
import com.AmrTm.StoreRestAPI.Entity.Item;
import com.AmrTm.StoreRestAPI.Exception.ItemNotFoundException;
import com.AmrTm.StoreRestAPI.LogDataBase.Log;
import com.AmrTm.StoreRestAPI.LogDataBase.LogData;
import com.AmrTm.StoreRestAPI.Service.FinancialServices;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/financial")
@Api(tags="financial")
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
	
	@PostMapping("/save/in/{name}/{mount}")
	@ApiOperation(value="Api for calculation of incoming money from sales of items", response=String.class)
	@ApiResponses({
			@ApiResponse(code=404, message="user not found"),
			@ApiResponse(code=404, message="sub item not found")
	})
	public String sales(@ApiParam("User purchased Item") @RequestBody Item item, 
						@ApiParam("Name sub item") @PathVariable String name, 
						@ApiParam("Number of arrival") @PathVariable int mount, 
						@ApiParam("Secret code user") @RequestParam(required=false) String id_user, 
						@ApiParam("Type sub item") @RequestParam String type,
						@ApiParam("Discon for this item") @RequestParam double discon) throws ItemNotFoundException{
		if(id_user != null) {
			item.setMount(mount);
			userRest.modifyUser(item, id_user);}
		else 
			log.info("anonymous customer item: "+item.getId()+" buyed");
		itemRest.deleteItemSubItem(name, item.getId(), type, mount);
		financialServices.income(financialServices.getCostDiscon(discon, item.getCost()).multiply(new BigDecimal(mount)));
		return financialServices.getAmountMoney().toString() + item.getCost().toString();
	}
	
	@PostMapping("/save/out/{name}/{mount}")
	@ApiOperation(value="Api for calculation of money out of purchasing items", response=String.class)
	@ApiResponses({
		@ApiResponse(code=404, message="sub item not found")
	})
	public String expense(@ApiParam("Store-bought item") @RequestBody Item item,
						  @ApiParam("Name sub item") @PathVariable String name, 
						  @ApiParam("Number of purchased items") @PathVariable int mount, 
						  @ApiParam("Type sub item") @RequestParam String type,
						  @ApiParam("Discon for all expense") @RequestParam double discon) throws ItemNotFoundException {
		itemRest.addItemSubItem(item, name, type, mount);
		financialServices.expenses(financialServices.getCostDiscon(discon, item.getCost().multiply(new BigDecimal(mount))));
		return "expense has been saved";
	}
	
	// using report sales minimal 1 day
	@PostMapping("/report")
	@ApiOperation(value="Api for reporting financial status", response=ResponseEntity.class)
	public ResponseEntity<Finance> reportFinance(){
		Finance finance = financialServices.report();
		return ResponseEntity.ok(finance);
	}
	
	@PostMapping("/logs")
	@ApiOperation(value="Api to get log data from sale or purchase item", response=List.class)
	@ApiResponses({
		@ApiResponse(code=404, message="date not saved")
	})
	public List<Log> getLogs(@ApiParam("date for searching log") @RequestBody Log date) {
		return logData.getLog(date.getDate());
	}
}
