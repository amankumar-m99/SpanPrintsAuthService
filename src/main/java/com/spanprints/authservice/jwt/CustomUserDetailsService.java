package com.spanprints.authservice.jwt;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.repository.AccountRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

	private AccountRepository repository;

	public CustomUserDetailsService(AccountRepository repository) {
		this.repository = repository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Account> optional = repository.findByUsername(username);
		if (optional.isPresent()) {
			Account account = optional.get();
			return account;
			// password("{noop}" + person.getName().toLowerCase())
//			return org.springframework.security.core.userdetails.User.builder()
//					.username(user.getUsername())
//					.password(user.getPassword()).roles(user.getRoles().toArray(new String[0]))
//					.build();
		}
		throw new UsernameNotFoundException(username);
	}
}
