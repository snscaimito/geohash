package com.testdouble.demo;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class DowWorker {
	
	public DowValue retrieveDowValue(LocalDate date) {
		// TODO call webservice to retrieve DOW value for given date
		
		return new DowValue(10000.0);
	}


}
