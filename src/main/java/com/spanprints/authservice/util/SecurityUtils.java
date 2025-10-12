package com.spanprints.authservice.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.exception.account.AccountNotFoundException;
import com.spanprints.authservice.service.AccountService;

@Component
public class SecurityUtils {

	private AccountService accountService;

	public SecurityUtils(AccountService accountService) {
		this.accountService = accountService;
	}

	public Account getActiveAccount() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails userDetails) {
            	Account account = accountService.getAccountByUsername(userDetails.getUsername());
            	return account;
            }
        }
        throw new AccountNotFoundException();
	}
}
