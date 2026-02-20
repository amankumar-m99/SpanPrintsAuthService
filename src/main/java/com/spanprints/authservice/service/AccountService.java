package com.spanprints.authservice.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.VerificationTokenResponse;
import com.spanprints.authservice.dto.account.CreateAccountRequest;
import com.spanprints.authservice.dto.password.ResetPasswordRequest;
import com.spanprints.authservice.dto.password.UpdatePasswordRequest;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.Role;
import com.spanprints.authservice.event.AccountRegisteredEvent;
import com.spanprints.authservice.exception.account.AccountNotFoundException;
import com.spanprints.authservice.exception.account.EmailAlreadyExistsException;
import com.spanprints.authservice.exception.account.UsernameAlreadyExistsException;
import com.spanprints.authservice.repository.AccountRepository;
import com.spanprints.authservice.repository.RoleRepository;
import com.spanprints.authservice.util.BasicUtils;

import jakarta.transaction.Transactional;

@Service
public class AccountService {

	private AccountRepository accountRepository;

	private RoleRepository roleRepository;

	private VerificationTokenService verificationTokenService;

	private ApplicationEventPublisher publisher;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public AccountService(AccountRepository accountRepository, RoleRepository roleRepository,
			VerificationTokenService verificationTokenService, ApplicationEventPublisher publisher) {
		this.accountRepository = accountRepository;
		this.roleRepository = roleRepository;
		this.publisher = publisher;
		this.verificationTokenService = verificationTokenService;
	}

	@Transactional
	public String createAccount(CreateAccountRequest request, String frontendBaseUrl) {
		// 1. Create and save the user (disabled)
		Account account = createAccountFromRequest(request);

		// 2. Generate and save verification token
		VerificationTokenResponse tokenResponse = verificationTokenService.getTokenResponseForAccount(account);

		// 3. Send verification email (outside transaction discussed below)
		publisher.publishEvent(
				new AccountRegisteredEvent(request.getEmail(), request.getUsername(), frontendBaseUrl, tokenResponse));

		return BasicUtils.maskEmail(request.getEmail());
	}

	private Account createAccountFromRequest(CreateAccountRequest request) {
		throwIfEmailAlreadyExists(request.getEmail());
		throwIfUsernameAlreadyExists(request.getUsername());
		Account account = Account.builder().email(request.getEmail()).username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword())).isLocked(false).isEnabled(false)
				.isAccountExpired(false).isCredentialExpired(false).build();
		if (request.getRoles() == null) {
			request.setRoles(Collections.emptySet());
		}
		account.setRoles(new HashSet<>());
		for (String roleName : request.getRoles()) {
			insertRoleIntoAccount(roleName, account);
		}
		boolean anyMatch = account.getRoles().stream().anyMatch(x -> x.getRoleName().equals("user"));
		if (!anyMatch) {
			insertRoleIntoAccount("User", account);
		}
		return accountRepository.save(account);
	}

	public boolean updatePassword(Account account, ResetPasswordRequest request) throws BadRequestException {
		if (!request.getPassword().equals(request.getConfirmPassword())) {
			throw new BadRequestException("Password and confirm password do not match.");
		}
		account.setPassword(passwordEncoder.encode(request.getPassword()));
		accountRepository.save(account);
		return true;
	}

	public boolean updatePassword(Account account, UpdatePasswordRequest request) throws BadRequestException {
		if (!account.getPassword().equals(passwordEncoder.encode(request.getCurrentPassword()))) {
			throw new BadRequestException("Current password is not correct.");
		}
		if (!request.getNewPassword().equals(request.getConfirmPassword())) {
			throw new BadRequestException("Password and confirm password do not match.");
		}
		account.setPassword(passwordEncoder.encode(request.getNewPassword()));
		accountRepository.save(account);
		return true;
	}

	private void insertRoleIntoAccount(String roleName, Account account) {
		Optional<Role> roleOptional = roleRepository.findByRoleName(roleName.toUpperCase());
		if (roleOptional.isPresent()) {
			account.getRoles().add(roleOptional.get());
		}
	}

	public void enableAccount(Account account) {
		account.setIsEnabled(true);
		accountRepository.save(account);
	}

	public List<Account> getAllAccounts() {
		return accountRepository.findAll();
	}

	public Account getAccountById(Long id) {
		return accountRepository.findById(id)
				.orElseThrow(() -> new AccountNotFoundException(String.format("No account found with id `%d`", id)));
	}

	public Account getAccountByUuid(String uuid) {
		return accountRepository.findByUuid(uuid).orElseThrow(
				() -> new AccountNotFoundException(String.format("No account found with uuid `%s`", uuid)));
	}

	public Account getAccountByUsername(String username) {
		return accountRepository.findByUsername(username).orElseThrow(
				() -> new AccountNotFoundException(String.format("No account found with username `%s`", username)));
	}

	public Account getAccountByEmail(String email) {
		return accountRepository.findByEmail(email).orElseThrow(
				() -> new AccountNotFoundException(String.format("No account found with email `%s`", email)));
	}

	public Account getAccountByUserDetails(UserDetails userDetails) {
		String username = userDetails.getUsername();
		return getAccountByUsername(username);
	}

	public SuccessResponseDto deleteAllAccounts() {
		accountRepository.deleteAll();
		return new SuccessResponseDto(HttpStatus.OK, "All accounts deleted.");
	}

	public SuccessResponseDto deleteById(Long id) {
		accountRepository.delete(getAccountById(id));
		return new SuccessResponseDto(HttpStatus.OK, String.format("Deleted account by id `%d`", id));
	}

	public SuccessResponseDto deleteByUuid(String uuid) {
		accountRepository.delete(getAccountByUuid(uuid));
		return new SuccessResponseDto(HttpStatus.OK, String.format("Deleted account by id `%s`", uuid));
	}

	private void throwIfEmailAlreadyExists(String email) {
		if (!accountRepository.findByEmail(email).isEmpty()) {
			throw new EmailAlreadyExistsException(
					String.format("An account already exsists with the email `%s`", email));
		}
	}

	private void throwIfUsernameAlreadyExists(String username) {
		if (!accountRepository.findByUsername(username).isEmpty()) {
			throw new UsernameAlreadyExistsException(
					String.format("An account already exsists with the username `%s`", username));
		}
	}
}
