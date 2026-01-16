package com.spanprints.authservice.entity;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spanprints.authservice.enums.Gender;

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
public class PersonalDetails extends AuditableBaseEntity {
	private String name;
	private Instant birthday;
	private Gender gender;

	@OneToOne
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	@JsonIgnore
	private Account account;
}
