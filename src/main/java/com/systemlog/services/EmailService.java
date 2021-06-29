package com.systemlog.services;

import org.springframework.mail.SimpleMailMessage;

import com.systemlog.domain.Pedido;

public interface EmailService {
	
	public void sendOrderConfirmationEmail(Pedido pedido);

	public void sendEmail(SimpleMailMessage msg);
}
