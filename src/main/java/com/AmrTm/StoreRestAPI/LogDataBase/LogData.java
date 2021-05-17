package com.AmrTm.StoreRestAPI.LogDataBase;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
/**
 * This class for management logging in app
 * @author Amar 
 * 
 * */

@Service
public class LogData {
	private List<Log> data = new LinkedList<>();
	public List<Log> getLog(LocalDate local) {
		return data.stream().filter(p -> p.getDate() == local).collect(Collectors.toList());
	}
	
	public void saveLog(String log) {
		data.add(new Log(LocalDate.now(), log));
	}
} 
