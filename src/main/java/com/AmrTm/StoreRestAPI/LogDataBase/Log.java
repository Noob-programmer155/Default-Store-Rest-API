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
}
