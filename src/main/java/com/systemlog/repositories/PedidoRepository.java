package com.systemlog.repositories;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.systemlog.domain.Cliente;
import com.systemlog.domain.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	
	@Transactional
	Page<Pedido> findByCliente (Cliente cliente, Pageable pageRequest); // Cuidado com erros nos imports 

}
