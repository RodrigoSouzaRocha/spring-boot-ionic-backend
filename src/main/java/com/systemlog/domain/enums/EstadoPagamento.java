package com.systemlog.domain.enums;

public enum EstadoPagamento {
	
	PENDENTE(0, "Pendente"),
	QUITADO(1, "Quitado"),
	CANCELADO(2, "Cancelado");
	
	private int cod;
	
	private String descricao;
	
	private EstadoPagamento (int cod, String descricao) { 
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static EstadoPagamento toEnum(Integer cod) { //static você consegue rodar o objeto mesmo não instanciado 
		
		if (cod == null)
			return null;
		
		for (EstadoPagamento tipo : EstadoPagamento.values()) {
			
			if( cod.equals(tipo.getCod()) ) {
				return tipo;
			}
			
		}
		
		throw new IllegalArgumentException("ID inválido: " + cod);

	}
	

}
