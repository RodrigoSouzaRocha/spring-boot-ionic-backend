package com.systemlog.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Environment env;
	
//	efetua a liberações dos metodos que poderam ser acessados sem segurança
	public static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**"
	};

//	efetua a liberações dos metodos que poderam ser acessados sem segurança
	public static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**",
			"/categorias/**"
	};

	
/*
 * 	Metodo necessario para que consigamos acessar os outros serviços
 * 	
 * 	http.cors().and().csrf().disable(); ( Desavilitamos o csrf pois não trabalharemos com sistemas em sessão, caso contrario será necessario habilita-lo e configura-lo ) 
 * 	http.authorizeRequests().antMatchers("PUBLIC_MATCHERS")permitAll(permite o acesso do que esta no vetor ).anyRequest().authenticated( Diz que é necessario ter acesso para os outros endPoints )
 * 
 *	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); (Dasabilita a criação de sessão pelo back-end) 
 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		if(Arrays.asList(env.getActiveProfiles()).contains("test"))			
			http.headers().frameOptions().disable();
		
		http.cors().and().csrf().disable();
		
		http.authorizeRequests()
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			.anyRequest().authenticated();

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}
	
	
}
