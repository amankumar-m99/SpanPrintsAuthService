package com.spanprints.authservice.dto;

import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.PersonalDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {

	private Account account;
	private PersonalDetails personalDetails;

}
