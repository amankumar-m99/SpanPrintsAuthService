package com.spanprints.authservice.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.RegisterRequestDto;
import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.Role;
import com.spanprints.authservice.exception.account.AccountNotFoundException;
import com.spanprints.authservice.exception.account.EmailAlreadyExistsException;
import com.spanprints.authservice.exception.account.UsernameAlreadyExistsException;
import com.spanprints.authservice.repository.AccountRepository;
import com.spanprints.authservice.repository.RoleRepository;

@Service
public class AccountService {

	@Autowired
	private AccountRepository repository;

	@Autowired
	private RoleRepository roleRepository;

	public Account register(RegisterRequestDto request) {
		throwIfEmailAlreadyExists(request.getEmail());
		throwIfUsernameAlreadyExists(request.getUsername());
		Account account = Account.builder().UUID(UUID.randomUUID().toString()).email(request.getEmail())
				.username(request.getUsername()).password(request.getPassword()).isLocked(false).isEnabled(false)
				.isAccountExpired(false).isCredentialExpired(false).build();
		if (request.getRoles() == null) {
			request.setRoles(Collections.emptySet());
		}
		account.setRoles(new HashSet<Role>());
		for (String roleName : request.getRoles()) {
			insertRoleIntoAccount(roleName, account);
		}
		boolean anyMatch = account.getRoles().stream().anyMatch(x -> x.getRoleName().equals("user"));
		if (!anyMatch) {
			insertRoleIntoAccount("User", account);
		}
		return repository.save(account);
	}

	private void insertRoleIntoAccount(String roleName, Account account) {
		Optional<Role> roleOptional = roleRepository.findByRoleName(roleName.toUpperCase());
		if (roleOptional.isPresent()) {
			account.getRoles().add(roleOptional.get());
		}
	}

	public void enableAccount(Account account) {
		account.setIsEnabled(true);
		repository.save(account);
	}

	public Account getAccountById(Long id) {
		return repository.findById(id)
				.orElseThrow(() -> new AccountNotFoundException(String.format("No account found with id `%d`", id)));
	}

	public List<Account> getAllAccounts() {
		return repository.findAll();
	}

	public SuccessResponseDto deleteById(Long id) {
		repository.delete(getAccountById(id));
		SuccessResponseDto response = new SuccessResponseDto(HttpStatus.OK,
				String.format("Deleted role by id `%d`", id));
		return response;
	}

	public SuccessResponseDto deleteAllAccounts() {
		repository.deleteAll();
		SuccessResponseDto response = new SuccessResponseDto(HttpStatus.OK, "All accounts deleted.");
		return response;
	}

	private void throwIfEmailAlreadyExists(String email) {
		if (!repository.findByEmail(email).isEmpty()) {
			throw new EmailAlreadyExistsException(
					String.format("An account already exsists with the email `%s`", email));
		}
	}

	private void throwIfUsernameAlreadyExists(String username) {
		if (!repository.findByUsername(username).isEmpty()) {
			throw new UsernameAlreadyExistsException(
					String.format("An account already exsists with the username `%s`", username));
		}
	}
}
