package com.systemlog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.systemlog.domain.Categoria;
import com.systemlog.domain.Produto;
import com.systemlog.repositories.CategoriaRepository;
import com.systemlog.repositories.ProdutoRepository;
import com.systemlog.services.exceptions.ObjectNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired // Autowired ele auto instancia a depndencia pelo spring 
	public ProdutoRepository repository;
	@Autowired // Autowired ele auto instancia a depndencia pelo spring 
	public CategoriaRepository categoriaRepository;
	
	public Produto find(Long id) {
		Optional<Produto> obj = repository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID:" +  id  + ", tipo: " + Produto.class.getName()));
		
	}
	
	public Page<Produto> search(String nome, List<Long> ids, Integer page, Integer linePage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linePage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAllById(ids);
		return repository.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
	

}
