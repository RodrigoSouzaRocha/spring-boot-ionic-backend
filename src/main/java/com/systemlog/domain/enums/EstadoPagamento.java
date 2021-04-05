package com.systemlog.domain.enums;

public enum EstadoPagamento {
	
	PENDENTE(0, "Pendente"),
	QUITADO(1, "Quitado"),
	CANCELADO(2, "Cancelado");
	
	private int codPagamento;
	private String labelPagamento;
	
	private EstadoPagamento (int codPagamento, String labelPagamento) {
		this.codPagamento = codPagamento;
		this.labelPagamento = labelPagamento;
	}

	public int getCodPagamento() {
		return codPagamento;
	}

	public String getLabelPagamento() {
		return labelPagamento;
	}
	
	public static EstadoPagamento toEnum(Integer codPagamento) { //static você consegue rodar o objeto mesmo não instanciado 
		
		if (codPagamento == null)
			return null;
		
		for (EstadoPagamento pagamentoTipo : EstadoPagamento.values()) {
			
			if( codPagamento.equals(pagamentoTipo.getCodPagamento()) ) {
				return pagamentoTipo;
			}
			
		}
		
		throw new IllegalArgumentException("ID inválido: " + codPagamento);

	}

	

}
