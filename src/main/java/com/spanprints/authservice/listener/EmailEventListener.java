package com.spanprints.authservice.listener;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.spanprints.authservice.event.AccountRegisteredEvent;
import com.spanprints.authservice.service.MailService;

@Component
public class EmailEventListener {

	private MailService mailService;

	public EmailEventListener(MailService mailService) {
		this.mailService = mailService;
	}

	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void handleAccountRegisteredEvent(AccountRegisteredEvent event) {
		mailService.sendVerificationMail(event.getEmail(), event.getUsername(), event.getFrontendBaseUrl(), event.getTokenResponse());
	}
}
