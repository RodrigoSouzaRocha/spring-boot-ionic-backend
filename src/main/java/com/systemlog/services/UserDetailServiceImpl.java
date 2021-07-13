package com.systemlog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.systemlog.domain.Cliente;
import com.systemlog.repositories.ClienteRepository;
import com.systemlog.security.UserSpringSecurity;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Cliente cli = repo.findByEmail(email);
		
		if (cli == null)
			throw new UsernameNotFoundException(email);
		
		return new UserSpringSecurity(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
	
	}

}
