package com.systemlog.services.validator;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.systemlog.domain.enums.TipoCliente;
import com.systemlog.dto.ClienteNewDTO;
import com.systemlog.resources.exceptions.FieldMessage;
import com.systemlog.services.validator.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		if(objDto.getTipoCliente().equals(TipoCliente.PESSOA_FISICA) && !BR.isValidCPF(objDto.getCpfcnpj()))
			list.add(new FieldMessage("Cpfcnpj", "CPF inválido!"));
		if(objDto.getTipoCliente().equals(TipoCliente.PESSOA_JURIDICA) && !BR.isValidCNPJ(objDto.getCpfcnpj()))
			list.add(new FieldMessage("Cpfcnpj", "CNPJ inválido!"));

		
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}