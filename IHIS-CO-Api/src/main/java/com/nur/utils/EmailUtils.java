package com.nur.utils;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailUtils { 
	
	Logger logger = LoggerFactory.getLogger(EmailUtils.class);

	@Autowired
	private JavaMailSender mailSender;

	public boolean sendEmail(String to, String subject, String body, File file) {
		boolean isSent = false;
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body, true);
			
			helper.addAttachment(file.getName(), file);

			mailSender.send(mimeMessage);
			isSent = true;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return isSent;
	}
}