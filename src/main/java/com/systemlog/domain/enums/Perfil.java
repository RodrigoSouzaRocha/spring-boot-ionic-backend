package com.systemlog.domain.enums;

public enum Perfil {
	
	ADMIN(0, "ROLE_ADMIN"), 	//ROLE é exigencia do Spring conforme documentação 
	CLIENTE(1, "ROLE_CLIENTE");
	
	private int cod;
	
	private String descricao;
	
	private Perfil (int cod, String descricao) { 
		this.cod = cod;
		this.descricao = descricao;
	}

	public int getCod() {
		return cod;
	}

	public String getDescricao() {
		return descricao;
	}
	
	public static Perfil toEnum(Integer cod) { //static você consegue rodar o objeto mesmo não instanciado 
		
		if (cod == null)
			return null;
		
		for (Perfil tipo : Perfil.values()) {
			
			if( cod.equals(tipo.getCod()) ) {
				return tipo;
			}
			
		}
		
		throw new IllegalArgumentException("ID inválido: " + cod);

	}

	

}
