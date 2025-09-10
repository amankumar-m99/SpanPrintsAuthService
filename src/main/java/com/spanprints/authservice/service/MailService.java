package com.spanprints.authservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.TokenResponseDto;
import com.spanprints.authservice.mailtemplate.VerificationLinkMailTemplate;

import jakarta.mail.internet.MimeMessage;

@Service
public class MailService {

	@Value("${mainappname:SpanPrints}")
	private String appName;

	@Value("${spring.mail.username}@gmail.com")
	private String supportEmailAddress;

	private JavaMailSender mailSender;

	public MailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	@Async()
	// Use @Async("emailExecutor") if you want a custom configuration for executer-service
	public void sendVerificationMail(String toEmailAddress, String username, TokenResponseDto tokenResponse) {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false);
			mimeMessageHelper.setTo(toEmailAddress);
			mimeMessageHelper.setSubject("Verification mail");
			String link = "http://localhost:8080/auth/verify?token=" + tokenResponse.getToken();
			String mailContenText = VerificationLinkMailTemplate.buildHtmlContent(appName, supportEmailAddress, link,
					tokenResponse.getExpiry(), username);
			mimeMessageHelper.setText(mailContenText, true);
			mailSender.send(mimeMessage);
		} catch (Exception e) {
		}

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
