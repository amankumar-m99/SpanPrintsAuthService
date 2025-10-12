package com.spanprints.authservice.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.jsonwebtoken.lang.Collections;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Account implements UserDetails {

	private static final long serialVersionUID = 2409871067906954451L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String uuid;
	private String email;
	private String username;
	@JsonIgnore
	private String password;
	private Boolean isLocked;
	private Boolean isEnabled;
	private Boolean isAccountExpired;
	private Boolean isCredentialExpired;
	@CreatedDate
//    @Column(nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
	@JoinTable(name = "ACCOUNT_ROLE", joinColumns = { @JoinColumn(name = "ACCOUNT_ID") }, inverseJoinColumns = {
			@JoinColumn(name = "ROLE_ID") })
	private Set<Role> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();
		getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName()));
		});
		return authorities;
	}

	@OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private VerificationToken verificationToken;

	@JsonProperty("verificationTokenId")
	public Long getVerificationTokenId() {
		return verificationToken != null ? verificationToken.getId() : null;
	}

	@OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private PersonalDetails personalDetails;

	@JsonProperty("personalDetailsId")
	public Long getPersonalDetailsId() {
		return personalDetails != null ? personalDetails.getId() : null;
	}

	@OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private ProfilePic profilePic;

	@JsonProperty("profilePicId")
	public Long getProfilePicId() {
		return profilePic != null ? profilePic.getId() : null;
	}

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<PrintJob> printJobs;

	@JsonProperty("printJobIds")
	public List<Long> getPrintJobIds() {
		if (printJobs == null) {
			return Collections.emptyList();
		}
		List<Long> ids = printJobs.stream().map(o -> o.getId()).collect(Collectors.toList());
		return ids;
	}

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Expense> expenses;

	@JsonProperty("expenseIds")
	public List<Long> getExpenseIds() {
		if (expenses == null) {
			return Collections.emptyList();
		}
		List<Long> ids = expenses.stream().map(o -> o.getId()).collect(Collectors.toList());
		return ids;
	}

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Ledger> ledger;

	@JsonProperty("ledgerIds")
	public List<Long> getLedgerIds() {
		if (ledger == null) {
			return Collections.emptyList();
		}
		List<Long> ids = ledger.stream().map(o -> o.getId()).collect(Collectors.toList());
		return ids;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !(getIsAccountExpired());
	}

	@Override
	public boolean isAccountNonLocked() {
		return !(getIsLocked());
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !(getIsCredentialExpired());
	}

	@Override
	public boolean isEnabled() {
		return getIsEnabled();
	}

}
