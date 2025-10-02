package com.spanprints.authservice.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spanprints.authservice.enums.Gender;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class PersonalDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private LocalDate birthday;
	private Gender gender;
	
	@OneToOne
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	@JsonIgnore
	private Account account;

	@JsonProperty("accountId") // will be included in JSON
	public Long getAccountId() {
		return account != null ? account.getId() : null;
	}

}
