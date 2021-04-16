package com.systemlog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systemlog.domain.Cliente;
import com.systemlog.repositories.ClienteRepository;
import com.systemlog.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired // Autowired ele auto instancia a depndencia pelo spring 
	public ClienteRepository clienteRepository;
	
	public Cliente find(Long id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID:" +  id  + ", tipo: " + Cliente.class.getName()));
		
	}

}
