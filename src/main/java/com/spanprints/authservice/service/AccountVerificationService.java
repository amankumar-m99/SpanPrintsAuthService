package com.spanprints.authservice.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.TokenResponseDto;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.AccountVerificationToken;
import com.spanprints.authservice.exception.account.verification.AccountVerificationTokenAlreadyUsedException;
import com.spanprints.authservice.exception.account.verification.AccountVerificationTokenExpiredException;
import com.spanprints.authservice.exception.account.verification.AccountVerificationTokenNotFoundException;
import com.spanprints.authservice.repository.AccountVerificationRepository;

@Service
public class AccountVerificationService {

	@Autowired
	private AccountVerificationRepository repository;

	public TokenResponseDto getTokenResponseForAccount(Account account) {
		AccountVerificationToken tokenForAccount = addTokenForAccount(account);
		return new TokenResponseDto(tokenForAccount.getToken(), tokenForAccount.getExpiryDate());
	}

	public AccountVerificationToken addTokenForAccount(Account account) {
		String uuidToken = UUID.randomUUID().toString();
		LocalDateTime expiry = LocalDateTime.now().plusDays(1);
		AccountVerificationToken token = new AccountVerificationToken(null, uuidToken, expiry, false, account);
		return repository.save(token);
	}

	public Account verifyToken(String token) {
		return repository.findByToken(token).map(verificationToken -> {
			if(verificationToken.getIsUsed().equals(true)) {
				throw new AccountVerificationTokenAlreadyUsedException("Verification token has already been used.");
			}
			if (verificationToken.getExpiryDate().isBefore(java.time.LocalDateTime.now())) {
				throw new AccountVerificationTokenExpiredException("Verification token is expired.");
			}
			verificationToken.setIsUsed(true);
			repository.save(verificationToken);
			return verificationToken.getAccount();
		}).orElseThrow(
				() -> new AccountVerificationTokenNotFoundException(String.format("No token found as `%s`", token)));
	}
}
