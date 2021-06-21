package com.systemlog.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.systemlog.domain.ItemPedido;
import com.systemlog.domain.PagamentoComBoleto;
import com.systemlog.domain.Pedido;
import com.systemlog.domain.enums.EstadoPagamento;
import com.systemlog.repositories.ItemPedidoRepository;
import com.systemlog.repositories.PagamentoRepository;
import com.systemlog.repositories.PedidoRepository;
import com.systemlog.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired // Autowired ele auto instancia a depndencia pelo spring 
	private PedidoRepository repo;
	@Autowired
	private BoletoServices boletoServices;
	@Autowired
	private PagamentoRepository pagamentoRepository;
	@Autowired
	private ProdutoService produtoService;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	public Pedido find(Long id) {
		Optional<Pedido> obj = repo.findById(id);
		
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! ID:" +  id  + ", tipo: " + Pedido.class.getName()));
		
	}
	
	@Transactional
	public Pedido insert(Pedido obj) {
		
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			
			boletoServices.preencherPagamentoComBoleto(pagto, obj.getInstante());
			
		}
		
		obj = repo.save(obj); // salva o objeto do pedido no banco de dados
		
		pagamentoRepository.save(obj.getPagamento()); // salva o tipo de pagamento no banco de dados
		
		for(ItemPedido ip : obj.getItens()) {
			ip.setDesconto(null);
			ip.setPreco(produtoService.find(ip.getProduto().getId()).getPreco());
			ip.setPedido(obj);
		}
		
		itemPedidoRepository.saveAll(obj.getItens());
		
		return obj;
	}

}
