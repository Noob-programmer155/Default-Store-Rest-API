package com.AmrTm.StoreRestAPI.LogDataBase;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
/**
 * This class for management database logging in app
 * @author Amar 
 * 
 * */

@Service
public class LogData {
	private List<Log> data = new LinkedList<>();
	public List<Log> getLog(LocalDate local) throws NullPointerException{
		try {
			return data.stream().filter(p -> p.getDate().equals(local)).collect(Collectors.toList());}
		catch(NoSuchElementException | NullPointerException ex) {
			throw new NullPointerException("date not saved");
		}
	}
	public void saveLog(String log) {
		data.add(new Log(LocalDate.now(), log));
	}
} 
