package com.systemlog.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.systemlog.domain.Cidade;
import com.systemlog.domain.Cliente;
import com.systemlog.domain.Endereco;
import com.systemlog.domain.enums.Perfil;
import com.systemlog.dto.ClienteDTO;
import com.systemlog.dto.ClienteNewDTO;
import com.systemlog.repositories.ClienteRepository;
import com.systemlog.repositories.EnderecoRepository;
import com.systemlog.security.UserSpringSecurity;
import com.systemlog.services.exceptions.AuthorizationException;
import com.systemlog.services.exceptions.DataIntegretyException;
import com.systemlog.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired // Autowired ele auto instancia a depndencia pelo spring 
	public ClienteRepository repository;
	
	@Autowired
	public EnderecoRepository enderecoRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public Cliente find(Long id) {
		UserSpringSecurity user = UserService.authenticated(); 
		
		if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado!");
		}
		
		Optional<Cliente> obj = repository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID:" +  id  + ", tipo: " + Cliente.class.getName()));
		
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj = repository.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repository.save(newObj);

	}

	public void delete(Long id) {		
		find(id);
		
		try {
			repository.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegretyException("Não é possivel excluir um cliente porque há entidades relacionadas! ");
		}
	
	}

	public List<Cliente> findAll() {
		return repository.findAll();
	}

	public Page<Cliente> findPage(Integer page, Integer linePage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linePage, Direction.valueOf(direction), orderBy);
		return repository.findAll(pageRequest);
	}
	
	public Cliente fromDTO (ClienteDTO objDTO) {
		
		return new Cliente(objDTO.getId(), objDTO.getNome(), objDTO.getEmail(), null, null, null);
		
	}
	
	public Cliente fromDTO (ClienteNewDTO objDTO) {
		Cliente cli = new Cliente(null, objDTO.getNome(), objDTO.getEmail(), objDTO.getCpfcnpj(), objDTO.getTipoCliente(), bCryptPasswordEncoder.encode(objDTO.getSenha()));
		Cidade cid = new Cidade(Long.valueOf(objDTO.getCidadeId()), null , null);
		Endereco end = new Endereco(null, objDTO.getLogradouro(), objDTO.getNumero(), objDTO.getComplemento(), objDTO.getBairro(), objDTO.getCep(), cli, cid);
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDTO.getTelefone1());
		
		if(objDTO.getTelefone2() != null)
			cli.getTelefones().add(objDTO.getTelefone2());
		
		if(objDTO.getTelefone3() != null)
			cli.getTelefones().add(objDTO.getTelefone2());
		
		return cli;
		
	}
	
	private void updateData(Cliente newObj, Cliente obj) {
		
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		
	
	}
	

}
