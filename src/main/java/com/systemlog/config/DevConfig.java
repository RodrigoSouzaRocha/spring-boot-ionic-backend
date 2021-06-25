package com.systemlog.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.systemlog.services.DBServices;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBServices dbServices;
	
	@Bean
	public boolean InstantiateDatabase() throws ParseException {
		
		dbServices.InstatiateTestDatabase();
		
		return true;
		
	}

}
