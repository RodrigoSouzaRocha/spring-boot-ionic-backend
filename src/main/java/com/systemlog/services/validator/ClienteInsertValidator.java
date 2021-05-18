package com.systemlog.services.validator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.systemlog.domain.Cliente;
import com.systemlog.domain.enums.TipoCliente;
import com.systemlog.dto.ClienteNewDTO;
import com.systemlog.repositories.ClienteRepository;
import com.systemlog.resources.exceptions.FieldMessage;
import com.systemlog.services.validator.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();
		Cliente cliente = null;

		if(objDto.getTipoCliente().equals(TipoCliente.PESSOA_FISICA) && !BR.isValidCPF(objDto.getCpfcnpj()))
			list.add(new FieldMessage("Cpfcnpj", "CPF inválido!"));
		if(objDto.getTipoCliente().equals(TipoCliente.PESSOA_JURIDICA) && !BR.isValidCNPJ(objDto.getCpfcnpj()))
			list.add(new FieldMessage("Cpfcnpj", "CNPJ inválido!"));
		
		cliente = repo.findByEmail(objDto.getEmail());
		
		if (cliente != null)
			list.add(new FieldMessage("email", "email já cadastrado em nossa base de dados"));

		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}