package com.systemlog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.systemlog.domain.Categoria;
import com.systemlog.dto.CategoriaDTO;
import com.systemlog.repositories.CategoriaRepository;
import com.systemlog.services.exceptions.DataIntegretyException;
import com.systemlog.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired // Autowired ele auto instancia a depndencia pelo spring 
	public CategoriaRepository categoriaRepository;
	
	public Categoria find(Long id) {
		Optional<Categoria> obj = categoriaRepository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID:" +  id  + ", tipo: " + Categoria.class.getName()));
		
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return categoriaRepository.save(obj);
	}

	public Categoria update(Categoria obj) {		
		find(obj.getId());
		return categoriaRepository.save(obj);

	}

	public void delete(Long id) {		
		find(id);
		try {
			categoriaRepository.deleteById(id);
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegretyException("Não é possivel excluir uma categoria que possui produtos! ");
		}
	
	}

	public List<Categoria> findAll() {
		return categoriaRepository.findAll();
	}

	public Page<Categoria> findPage(Integer page, Integer linePage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linePage, Direction.valueOf(direction), orderBy);
		return categoriaRepository.findAll(pageRequest);
	}
	
	public Categoria fromDTO (CategoriaDTO objDTO) {
		
		return new Categoria(objDTO.getId(), objDTO.getNome());
		
	}
	
}
