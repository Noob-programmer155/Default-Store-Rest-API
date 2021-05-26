package com.AmrTm.StoreRestAPI.LogDataBase;

import java.time.LocalDate;
<<<<<<< HEAD
import java.util.List;
=======
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
>>>>>>> a801fe1aca50ff6ebbe8fbba6a77bf8afe0ec7cb
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
/**
<<<<<<< HEAD
 * This class for management logging in app
=======
 * This class for management database logging in app
>>>>>>> a801fe1aca50ff6ebbe8fbba6a77bf8afe0ec7cb
 * @author Amar 
 * 
 * */

@Service
public class LogData {
<<<<<<< HEAD
	private List<Log> data;
	public List<Log> getLog(LocalDate local) {
		return data.stream().filter(p -> p.getDate() == local).collect(Collectors.toList());
=======
	private List<Log> data = new LinkedList<>();
	public List<Log> getLog(LocalDate local) throws NullPointerException{
		try {
			return data.stream().filter(p -> p.getDate().equals(local)).collect(Collectors.toList());}
		catch(NoSuchElementException | NullPointerException ex) {
			throw new NullPointerException("date not saved");
		}
>>>>>>> a801fe1aca50ff6ebbe8fbba6a77bf8afe0ec7cb
	}
	
	public void saveLog(String log) {
		data.add(new Log(LocalDate.now(), log));
	}
} 
