package com.systemlog.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import com.systemlog.domain.Cliente;
import com.systemlog.services.validator.ClienteUpdate;

@ClienteUpdate
public class ClienteDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 120, message = "O tamanho do campo deve ser entre 5 a 120 caracteres")
	private String nome;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Email(message = "Email inválido")
	private String email;

	public ClienteDTO() {
		
	}

	public ClienteDTO( Cliente obj) {
		id = obj.getId();
		nome = obj.getNome();
		email = obj.getEmail();
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
}
