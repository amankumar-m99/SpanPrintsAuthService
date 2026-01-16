package com.spanprints.authservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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
public class ProfilePic extends AuditableBaseEntity {

	private String name;
	private String url;

	@OneToOne
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	@JsonIgnore
	private Account account;
}
