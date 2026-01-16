package com.spanprints.authservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.VerificationTokenResponse;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.VerificationToken;
import com.spanprints.authservice.exception.verificationtoken.VerificationTokenAlreadyUsedException;
import com.spanprints.authservice.exception.verificationtoken.VerificationTokenExpiredException;
import com.spanprints.authservice.exception.verificationtoken.VerificationTokenNotFoundException;
import com.spanprints.authservice.repository.AccountVerificationRepository;

@Service
public class VerificationTokenService {

	@Autowired
	private AccountVerificationRepository accountVerificationRepository;

	public List<VerificationToken> getAllTokens() {
		return accountVerificationRepository.findAll();
	}

	public VerificationTokenResponse getTokenResponseForAccount(Account account) {
		VerificationToken tokenForAccount = addTokenForAccount(account);
		return new VerificationTokenResponse(tokenForAccount);
	}

	public VerificationToken addTokenForAccount(Account account) {
		String uuidToken = UUID.randomUUID().toString();
		LocalDateTime expiry = LocalDateTime.now().plusDays(1);
		VerificationToken token = new VerificationToken(uuidToken, expiry, false, account);
		return accountVerificationRepository.save(token);
	}

	public VerificationToken getTokenById(Long id) {
		return accountVerificationRepository.findById(id).orElse(null);
	}

	public void deleteToken(VerificationToken token) {
		accountVerificationRepository.delete(token);
	}

	public SuccessResponseDto deleteTokenById(Long id) {
		accountVerificationRepository.delete(getTokenById(id));
		return new SuccessResponseDto(HttpStatus.OK, String.format("Deleted token by id `%d`", id));
	}

	public Account verifyToken(String token) {
		return accountVerificationRepository.findByToken(token).map(verificationToken -> {
			if (verificationToken.getIsUsed().equals(true)) {
				throw new VerificationTokenAlreadyUsedException("Verification token has already been used.");
			}
			if (verificationToken.getExpiryDate().isBefore(java.time.LocalDateTime.now())) {
				throw new VerificationTokenExpiredException("Verification token is expired.");
			}
			verificationToken.setIsUsed(true);
			accountVerificationRepository.save(verificationToken);
			return verificationToken.getAccount();
		}).orElseThrow(
				() -> new VerificationTokenNotFoundException(String.format("No token found as `%s`", token)));
	}

}
