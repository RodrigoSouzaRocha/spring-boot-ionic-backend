package com.systemlog.domain;

public enum TipoCliente {
	
	PESSOA_FISICA(0, "PESSOA FÍSICA"),
	PESSOA_JURIDICA(1, "PESSOA JURÍDICA");
	
	private int codPessoa;
	private String labelPessoa;
	
	private TipoCliente (int codPessoa, String labelPessoa) {
		this.codPessoa = codPessoa;
		this.labelPessoa = labelPessoa;
	}

	public int getCodPessoa() {
		return codPessoa;
	}

	public String getLabelPessoa() {
		return labelPessoa;
	}
	
	public static TipoCliente toEnum(Integer codPessoa) { //static você consegue rodar o objeto mesmo não instanciado 
		
		if (codPessoa == null)
			return null;
		
		for (TipoCliente clienteTipo : TipoCliente.values()) {
			
			if( codPessoa.equals(clienteTipo.getCodPessoa()) ) {
				return clienteTipo;
			}
			
		}
		
		throw new IllegalArgumentException("ID inválido: " + codPessoa);

	}

}
