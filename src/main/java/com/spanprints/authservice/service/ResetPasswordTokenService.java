package com.spanprints.authservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.password.ResetPasswordTokenResponse;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.ResetPasswordToken;
import com.spanprints.authservice.exception.token.TokenAlreadyUsedException;
import com.spanprints.authservice.exception.token.TokenExpiredException;
import com.spanprints.authservice.exception.token.TokenNotFoundException;
import com.spanprints.authservice.repository.ResetPasswordTokenRepository;

@Service
public class ResetPasswordTokenService {

	@Autowired
	private ResetPasswordTokenRepository resetPasswordTokenRepository;

	LocalDateTime expiry;

	public ResetPasswordTokenService() {
		this.expiry = LocalDateTime.now().plusMinutes(20);
	}

	public List<ResetPasswordToken> getAllTokens() {
		return resetPasswordTokenRepository.findAll();
	}

	public ResetPasswordTokenResponse getTokenResponseForAccount(Account account) {
		ResetPasswordToken tokenForAccount = addTokenForAccount(account);
		return new ResetPasswordTokenResponse(tokenForAccount);
	}

	public ResetPasswordToken addTokenForAccount(Account account) {
		String uuidToken = UUID.randomUUID().toString();
		ResetPasswordToken token = new ResetPasswordToken(uuidToken, expiry, false, account);
		return resetPasswordTokenRepository.save(token);
	}

	public ResetPasswordToken getTokenById(Long id) {
		return resetPasswordTokenRepository.findById(id).orElseThrow(
				() -> new TokenNotFoundException(String.format("No token found by id  `%d`", id)));
	}

	public void deleteToken(ResetPasswordToken token) {
		resetPasswordTokenRepository.delete(token);
	}

	public SuccessResponseDto deleteTokenById(Long id) {
		resetPasswordTokenRepository.delete(getTokenById(id));
		return new SuccessResponseDto(HttpStatus.OK, String.format("Deleted token by id `%d`", id));
	}

	public ResetPasswordToken verifyTokenBefore(String resetPasswordToken) {
		return resetPasswordTokenRepository.findByToken(resetPasswordToken).map(token -> {
			if (token.getIsUsed().equals(true)) {
				throw new TokenAlreadyUsedException("Verification token has already been used.");
			}
			if (token.getExpiryDate().isBefore(java.time.LocalDateTime.now())) {
				throw new TokenExpiredException("Verification token is expired.");
			}
			return token;
		}).orElseThrow(() -> new TokenNotFoundException(
				String.format("No token found as `%s`", resetPasswordToken)));
	}

	public void markTokenAsUsed(ResetPasswordToken token) {
		token.setIsUsed(true);
		resetPasswordTokenRepository.save(token);
	}

	public void markTokenAsUsed(String token) {
		ResetPasswordToken passwordToken = resetPasswordTokenRepository.findByToken(token).orElseThrow(
				() -> new TokenNotFoundException(String.format("No token found as `%s`", token)));
		markTokenAsUsed(passwordToken);
	}

}
