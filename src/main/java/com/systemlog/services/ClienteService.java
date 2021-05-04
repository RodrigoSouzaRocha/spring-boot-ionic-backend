package com.systemlog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.systemlog.domain.Cliente;
import com.systemlog.dto.ClienteDTO;
import com.systemlog.repositories.ClienteRepository;
import com.systemlog.services.exceptions.DataIntegretyException;
import com.systemlog.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired // Autowired ele auto instancia a depndencia pelo spring 
	public ClienteRepository clienteRepository;
	
	public Cliente find(Long id) {
		Optional<Cliente> obj = clienteRepository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID:" +  id  + ", tipo: " + Cliente.class.getName()));
		
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return clienteRepository.save(newObj);

	}

	public void delete(Long id) {		
		find(id);
		
		try {
			clienteRepository.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegretyException("Não é possivel excluir um cliente porque há entidades relacionadas! ");
		}
	
	}

	public List<Cliente> findAll() {
		return clienteRepository.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linePage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linePage, Direction.valueOf(direction), orderBy);
		return clienteRepository.findAll(pageRequest);
	}
	
	public Cliente fromDTO (ClienteDTO objDTO) {
		
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null);
		
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		
	
	}
	

}
