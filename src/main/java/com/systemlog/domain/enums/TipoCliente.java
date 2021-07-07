package com.systemlog.domain.enums;

public enum TipoCliente {
	
	PESSOA_FISICA(0, "PESSOA FÍSICA"),
	PESSOA_JURIDICA(1, "PESSOA JURÍDICA");
	
	private int cod;
	
	private String descricao;
	
	private TipoCliente (int cod, String descricao) { 
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static TipoCliente toEnum(Integer cod) { //static você consegue rodar o objeto mesmo não instanciado 
		
		if (cod == null)
			return null;
		
		for (TipoCliente tipo : TipoCliente.values()) {
			
			if( cod.equals(tipo.getCod()) ) {
				return tipo;
			}
			
		}
		
		throw new IllegalArgumentException("ID inválido: " + cod);

	}

}
