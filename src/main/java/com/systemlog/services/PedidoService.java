package com.systemlog.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systemlog.domain.Pedido;
import com.systemlog.repositories.PedidoRepository;
import com.systemlog.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired // Autowired ele auto instancia a depndencia pelo spring 
	public PedidoRepository pedidoRepository;
	
	public Pedido find(Long id) {
		Optional<Pedido> obj = pedidoRepository.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID:" +  id  + ", tipo: " + Pedido.class.getName()));
		
	}

}
