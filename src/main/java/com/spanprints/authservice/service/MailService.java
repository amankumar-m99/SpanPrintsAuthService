package com.spanprints.authservice.service;

import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.TokenResponseDto;

import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

	private JavaMailSender mailSender;

	public MailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendVerificationMail(String emailAddress, TokenResponseDto tokenResponse) {
	}

	public boolean send(String to, String subject, String text, String[] cc, String[] bcc, Resource resource) {
		try {
			// 1. Create an empty message
			MimeMessage mimeMessage = mailSender.createMimeMessage();

			// 2. Fill details
			boolean isMultipart = (resource != null);
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, isMultipart);
			mimeMessageHelper.setTo(to);
			if (cc != null && cc.length > 0) {
				mimeMessageHelper.setCc(cc);
			}
			if (bcc != null && bcc.length > 0) {
				mimeMessageHelper.setBcc(bcc);
			}
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(text);
			if (isMultipart) {
				mimeMessageHelper.addAttachment(resource.toString(), resource);
			}
			// 3. Send mail
			mailSender.send(mimeMessage);
			return true;
		} catch (Exception e) {
		}
		return false;
	}
}
