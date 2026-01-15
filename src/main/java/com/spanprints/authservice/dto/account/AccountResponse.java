package com.spanprints.authservice.dto.account;

import java.util.List;

import com.spanprints.authservice.dto.EntityResponseDto;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.Customer;
import com.spanprints.authservice.entity.Expense;
import com.spanprints.authservice.entity.Ledger;
import com.spanprints.authservice.entity.PrintJob;
import com.spanprints.authservice.entity.Role;
import com.spanprints.authservice.entity.Vendor;

import io.jsonwebtoken.lang.Collections;
import lombok.Getter;

@Getter
public class AccountResponse extends EntityResponseDto {

	private String email;
	private String username;
	private Boolean isLocked;
	private Boolean isEnabled;
	private Boolean isAccountExpired;
	private Boolean isCredentialExpired;
	private List<String> roles;
	private Long verificationTokenId;
	private Long personalDetailsId;
	private Long profilePicId;
	private List<Long> printJobIds;
	private List<Long> expenseIds;
	private List<Long> ledgerIds;
	private List<Long> customerIds;
	private List<Long> vendorIds;

	public AccountResponse(Account account) {
		super(account);
		this.email = account.getEmail();
		this.username = account.getUsername();
		this.isLocked = account.getIsLocked();
		this.isEnabled = account.getIsEnabled();
		this.isAccountExpired = account.getIsAccountExpired();
		this.isCredentialExpired = account.getIsCredentialExpired();
		this.roles = account.getRoles().stream().map(Role::getRoleName).toList();
		this.verificationTokenId = account.getVerificationToken() != null ? account.getVerificationToken().getId()
				: null;
		this.personalDetailsId = account.getPersonalDetails() != null ? account.getPersonalDetails().getId() : null;
		this.profilePicId = account.getProfilePic() != null ? account.getProfilePic().getId() : null;
		this.printJobIds = account.getPrintJobs() != null
				? account.getPrintJobs().stream().map(PrintJob::getId).toList()
				: Collections.emptyList();
		this.expenseIds = account.getExpenses() != null ? account.getExpenses().stream().map(Expense::getId).toList()
				: Collections.emptyList();
		this.ledgerIds = account.getLedger() != null ? account.getLedger().stream().map(Ledger::getId).toList()
				: Collections.emptyList();
		this.customerIds = account.getCustomers() != null
				? account.getCustomers().stream().map(Customer::getId).toList()
				: Collections.emptyList();
		this.vendorIds = account.getVendors() != null ? account.getVendors().stream().map(Vendor::getId).toList()
				: Collections.emptyList();
	}
}
