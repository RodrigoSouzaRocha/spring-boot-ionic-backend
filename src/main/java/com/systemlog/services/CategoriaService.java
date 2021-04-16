package com.systemlog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systemlog.domain.Categoria;
import com.systemlog.repositories.CategoriaRepository;
import com.systemlog.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired // Autowired ele auto instancia a depndencia pelo spring 
	public CategoriaRepository categoriaRepository;
	
	public Categoria buscar(Long id) {
		Optional<Categoria> obj = categoriaRepository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID:" +  id  + ", tipo: " + Categoria.class.getName()));
		
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return categoriaRepository.save(obj);
	}

}
