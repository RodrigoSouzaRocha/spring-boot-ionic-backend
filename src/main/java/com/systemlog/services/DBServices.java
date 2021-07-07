package com.systemlog.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.systemlog.domain.Categoria;
import com.systemlog.domain.Cidade;
import com.systemlog.domain.Cliente;
import com.systemlog.domain.Endereco;
import com.systemlog.domain.Estado;
import com.systemlog.domain.ItemPedido;
import com.systemlog.domain.Pagamento;
import com.systemlog.domain.PagamentoComBoleto;
import com.systemlog.domain.PagamentoComCartao;
import com.systemlog.domain.Pedido;
import com.systemlog.domain.Produto;
import com.systemlog.domain.enums.EstadoPagamento;
import com.systemlog.domain.enums.Perfil;
import com.systemlog.domain.enums.TipoCliente;
import com.systemlog.repositories.CategoriaRepository;
import com.systemlog.repositories.CidadeRepository;
import com.systemlog.repositories.ClienteRepository;
import com.systemlog.repositories.EnderecoRepository;
import com.systemlog.repositories.EstadoRepository;
import com.systemlog.repositories.ItemPedidoRepository;
import com.systemlog.repositories.PagamentoRepository;
import com.systemlog.repositories.PedidoRepository;
import com.systemlog.repositories.ProdutoRepository;

@Service
public class DBServices {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private EstadoRepository estadoRepository;
	@Autowired
	private CidadeRepository cidadeRepository;
	@Autowired
	private EnderecoRepository enderecoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private PedidoRepository pedidoRepository;
	@Autowired
	private PagamentoRepository pagamentoRepository;	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void InstatiateTestDatabase() throws ParseException {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		Categoria cat3 = new Categoria(null, "cama mesa e banho");
		Categoria cat4 = new Categoria(null, "Eletronicos");
		Categoria cat5 = new Categoria(null, "Jardinagem");
		Categoria cat6 = new Categoria(null, "Decoracao");
		Categoria cat7 = new Categoria(null, "Perfumaria");
		
		Produto p1 = new Produto(null, "Computador", 2000.000);
		Produto p2 = new Produto(null, "Impressora", 800.000);
		Produto p3 = new Produto(null, "Mouse", 80.000);
		Produto p4 = new Produto(null, "Mesa de escritorio", 3000.000);
		Produto p5 = new Produto(null, "Toalha", 50.000);
		Produto p6 = new Produto(null, "Colcha", 200.000);
		Produto p7 = new Produto(null, "Tv true color", 1200.000);
		Produto p8 = new Produto(null, "Roçadeira", 800.000);
		Produto p9 = new Produto(null, "Abajour", 100.000);
		Produto p10 = new Produto(null, "Pendente", 180.000);
		Produto p11 = new Produto(null, "Shampoo", 90.000);
	
		cat1.getProdutos().addAll(Arrays.asList(p1,p2,p3));
		cat2.getProdutos().addAll(Arrays.asList(p2, p4));
		cat3.getProdutos().addAll(Arrays.asList(p5, p6));
		cat4.getProdutos().addAll(Arrays.asList(p1, p2, p3, p7));
		cat5.getProdutos().addAll(Arrays.asList(p8));
		cat6.getProdutos().addAll(Arrays.asList(p9, p10));
		cat7.getProdutos().addAll(Arrays.asList(p11));
		
		p1.getCategorias().addAll(Arrays.asList(cat1, cat4));
		p2.getCategorias().addAll(Arrays.asList(cat1,cat2, cat4));
		p3.getCategorias().addAll(Arrays.asList(cat1, cat4));		
		p4.getCategorias().addAll(Arrays.asList(cat2));		
		p5.getCategorias().addAll(Arrays.asList(cat3));		
		p6.getCategorias().addAll(Arrays.asList(cat3));		
		p7.getCategorias().addAll(Arrays.asList(cat4));		
		p8.getCategorias().addAll(Arrays.asList(cat5));		
		p9.getCategorias().addAll(Arrays.asList(cat6));		
		p10.getCategorias().addAll(Arrays.asList(cat6));		
		p11.getCategorias().addAll(Arrays.asList(cat7));		
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

		
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "Sao Paulo");
				
		Cidade c1 = new Cidade(null, "Ubelandia", est1);
		Cidade c2 = new Cidade(null, "Sao Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));
		
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		
		Cliente cli1 = new Cliente(null, "Maria silva", "rodrigo.souza@complog.com.br", "36378912377", TipoCliente.PESSOA_FISICA, bCryptPasswordEncoder.encode("123"));
		cli1.getTelefones().addAll(Arrays.asList("2763323", "93838393"));

		Cliente cli2 = new Cliente(null, "Rodrigo Souza", "rodrigosouzajava@gmail.com.br", "41988799821", TipoCliente.PESSOA_FISICA, bCryptPasswordEncoder.encode("123"));
		cli2.getTelefones().addAll(Arrays.asList("41611905", "987356382"));
		cli2.addPerfil(Perfil.ADMIN);
		
		Endereco e1 = new Endereco(null, "Rua flores", "300", "apt 303", "jardim", "38220834", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida matos", "105", "sala 800", "centro", "38777012", cli1, c2);
		Endereco e3 = new Endereco(null, "Avenida trindade", "122", null, "centro", "064040326", cli2, c2);
		
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		cli2.getEnderecos().addAll(Arrays.asList(e3));
		
		clienteRepository.saveAll(Arrays.asList(cli1, cli2));
		enderecoRepository.saveAll(Arrays.asList(e1,e2, e3));
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		
		Pedido ped1 = new Pedido(null, sdf.parse("30/09/2021 10:32"), cli1, e1);
		Pedido ped2 = new Pedido(null, sdf.parse("10/10/2021 19:35"), cli1, e2);
		
		Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pagto1);
		
		Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2021 00:00"), null);
		ped2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
		ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
		ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);
		
		ped1.getItens().addAll(Arrays.asList(ip1,ip2));
		ped2.getItens().addAll(Arrays.asList(ip3));
		
		p1.getItens().addAll(Arrays.asList(ip1));
		p2.getItens().addAll(Arrays.asList(ip3));
		p3.getItens().addAll(Arrays.asList(ip2));
	
		itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
		
	}

}
