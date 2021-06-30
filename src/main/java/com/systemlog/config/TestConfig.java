package com.systemlog.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.systemlog.services.DBServices;
import com.systemlog.services.EmailService;
import com.systemlog.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {
	
	@Autowired
	private DBServices dbServices;
	
	
	@Bean
	public boolean InstantiateDatabase() throws ParseException {
		dbServices.InstatiateTestDatabase();
		return true;
	}

	@Bean
	public EmailService emailService()  {
		return new MockEmailService();		
	}

}
