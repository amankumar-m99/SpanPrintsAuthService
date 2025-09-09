package com.spanprints.authservice.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Account implements UserDetails  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2409871067906954451L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String UUID;
	private String email;
	private String username;
	@JsonIgnore
	private String password;
	private Boolean isLocked;
	private Boolean isEnabled;
	private Boolean isAccountExpired;
	private Boolean isCredentialExpired;
	
	@ManyToMany(fetch=FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinTable(name = "ACCOUNT_ROLE", joinColumns = {@JoinColumn(name="ACCOUNT_ID")}, inverseJoinColumns = {@JoinColumn(name="ROLE_ID")})
	private Set<Role> roles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorities = new HashSet<>();
		getRoles().forEach(role->{
			authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRoleName()));
		});
		return authorities;
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
