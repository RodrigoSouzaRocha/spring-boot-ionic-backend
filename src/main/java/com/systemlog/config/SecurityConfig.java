package com.systemlog.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.systemlog.security.JWTAuthenticationFilter;
import com.systemlog.security.JWTAuthorizationFilter;
import com.systemlog.security.JWTUtil;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

 	@Autowired
 	private Environment env;
 	@Autowired
 	private JWTUtil jwtUtil;
 	
 	@Autowired
 	private UserDetailsService userDetailsService;
 	
//	efetua a liberações dos metodos que poderam ser acessados sem segurança
	public static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**"
	};

//	efetua a liberações dos metodos que poderam ser acessados sem segurança
	public static final String[] PUBLIC_MATCHERS_GET = {
			"/produtos/**",
			"/categorias/**",
			"/clientes/**"
	};

	
/*
 * 	Metodo necessario para que consigamos acessar os outros serviços
 * 	
 * 	http.cors().and().csrf().disable(); ( Desavilitamos o csrf pois não trabalharemos com sistemas em sessão, caso contrario será necessario habilita-lo e configura-lo ) 
 *
 * 	http.authorizeRequests().antMatchers("PUBLIC_MATCHERS")permitAll(permite o acesso do que esta no vetor ).anyRequest().authenticated( Diz que é necessario ter acesso para os outros endPoints )
 *
 *  --------------------------------------------------------------------------------------------------------------------------------------------------------------------
 *  Caso o console do banco h2 não funcione na base de teste adicionar o comando a baixo 
 *	
 *	@Autowired
 *	private Environment env;
 *	
 *	if(Arrays.asList(env.getActiveProfiles()).contains("test"))			
 *		http.headers().frameOptions().disable();
 *  --------------------------------------------------------------------------------------------------------------------------------------------------------------------
 *	
 *	http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); (Dasabilita a criação de sessão pelo back-end) 
 *
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
		
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();		
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

}
