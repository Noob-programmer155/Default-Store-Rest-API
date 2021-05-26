package com.AmrTm.StoreRestAPI.LogDataBase;

import java.time.LocalDate;

/**
 * this class for object logging in LogData service
 * @author Amar
 * */

public class Log {
	private LocalDate date;
	private String logs;
	public Log(LocalDate date, String logs) {
		super();
		this.date = date;
		this.logs = logs;
	}
	public LocalDate getDate() {
		return date;
	}
	public String getLogs() {
		return logs;
	}
<<<<<<< HEAD
=======
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public void setLogs(String logs) {
		this.logs = logs;
	}
>>>>>>> a801fe1aca50ff6ebbe8fbba6a77bf8afe0ec7cb
}
