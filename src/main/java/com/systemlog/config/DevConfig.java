package com.systemlog.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.systemlog.services.DBServices;
import com.systemlog.services.EmailService;
import com.systemlog.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {
	
	@Autowired
	private DBServices dbServices;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String strategy;
	
	@Bean
	public boolean InstantiateDatabase() throws ParseException {

		if (!"create".equals(strategy))
			return false;
		
		dbServices.InstatiateTestDatabase();		
		return true;
	}

	@Bean
	public EmailService emailService()  {
		return new SmtpEmailService();		
	}
}
