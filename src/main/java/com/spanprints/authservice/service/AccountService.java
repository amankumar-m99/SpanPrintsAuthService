package com.spanprints.authservice.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.RegisterRequestDto;
import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.TokenResponseDto;
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

	private AccountRepository accountRepo;

	private RoleRepository roleRepo;

	private VerificationTokenService verificationTokenService;

	private ApplicationEventPublisher publisher;

	public AccountService(AccountRepository accountRepo, RoleRepository roleRepo,
			VerificationTokenService verificationTokenService, ApplicationEventPublisher publisher) {
		this.accountRepo = accountRepo;
		this.roleRepo = roleRepo;
		this.publisher = publisher;
		this.verificationTokenService = verificationTokenService;
	}

	@Transactional
	public String register(RegisterRequestDto request) {
		// 1. Create and save the user (disabled)
		Account account = createAccount(request);

		// 2. Generate and save verification token
		TokenResponseDto tokenResponse = verificationTokenService.getTokenResponseForAccount(account);

		// 3. Send verification email (outside transaction � discussed below)
		publisher.publishEvent(new AccountRegisteredEvent(request.getEmail(), tokenResponse));

		return BasicUtils.maskEmail(request.getEmail());
	}

	private Account createAccount(RegisterRequestDto request) {
		throwIfEmailAlreadyExists(request.getEmail());
		throwIfUsernameAlreadyExists(request.getUsername());
		Account account = Account.builder().UUID(UUID.randomUUID().toString()).email(request.getEmail())
				.username(request.getUsername()).password(request.getPassword()).isLocked(false).isEnabled(false)
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
		return accountRepo.save(account);
	}

	private void insertRoleIntoAccount(String roleName, Account account) {
		Optional<Role> roleOptional = roleRepo.findByRoleName(roleName.toUpperCase());
		if (roleOptional.isPresent()) {
			account.getRoles().add(roleOptional.get());
		}
	}

	public void enableAccount(Account account) {
		account.setIsEnabled(true);
		accountRepo.save(account);
	}

	public Account getAccountById(Long id) {
		return accountRepo.findById(id)
				.orElseThrow(() -> new AccountNotFoundException(String.format("No account found with id `%d`", id)));
	}

	public List<Account> getAllAccounts() {
		return accountRepo.findAll();
	}

	public SuccessResponseDto deleteById(Long id) {
		accountRepo.delete(getAccountById(id));
		return new SuccessResponseDto(HttpStatus.OK, String.format("Deleted role by id `%d`", id));
	}

	public SuccessResponseDto deleteAllAccounts() {
		accountRepo.deleteAll();
		return new SuccessResponseDto(HttpStatus.OK, "All accounts deleted.");
	}

	private void throwIfEmailAlreadyExists(String email) {
		if (!accountRepo.findByEmail(email).isEmpty()) {
			throw new EmailAlreadyExistsException(
					String.format("An account already exsists with the email `%s`", email));
		}
	}

	private void throwIfUsernameAlreadyExists(String username) {
		if (!accountRepo.findByUsername(username).isEmpty()) {
			throw new UsernameAlreadyExistsException(
					String.format("An account already exsists with the username `%s`", username));
		}
	}
}
