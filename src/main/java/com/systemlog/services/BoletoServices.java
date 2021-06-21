package com.systemlog.services;

import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.systemlog.domain.PagamentoComBoleto;

@Service
public class BoletoServices {
	
	public void preencherPagamentoComBoleto(PagamentoComBoleto pagto, Date instanteDoPedido) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(instanteDoPedido);
		cal.add(Calendar.DAY_OF_MONTH, 7);
		pagto.setDataVencimento(cal.getTime());
	}

}
