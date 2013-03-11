package com.hanghang.codestore.mail;

import javax.mail.Message;

public abstract class MailSender implements MailSendable {
	
	public abstract boolean sendMail(String subject, String body) throws Exception;
	

	@Override
	public boolean sendMail(Message message) throws Exception {
		
		return false;
	}

}
