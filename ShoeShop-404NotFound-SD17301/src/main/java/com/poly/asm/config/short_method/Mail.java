package com.poly.asm.config.short_method;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.poly.asm.model.MailInfo2;
import com.poly.asm.model.User;
import com.poly.asm.service.MailerService2;

@Component
public class Mail {
	@Autowired
	private MailerService2 mailerService2;

	public void setMail(User user, String body) {
		MailInfo2 mailInfo2 = new MailInfo2();
		mailInfo2.setFrom("Shoe Shop 404<khanhttpc03027@fpt.edu.vn>");
		mailInfo2.setTo(user.getEmail());
		mailInfo2.setSubject("SHOE SHOP CODE");
		mailInfo2.setBody(body);
		mailerService2.queue(mailInfo2);
	}
}
