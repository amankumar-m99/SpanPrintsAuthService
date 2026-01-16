package com.spanprints.authservice.dto.personaldetails;

import java.time.Instant;

import com.spanprints.authservice.dto.EntityResponseDto;
import com.spanprints.authservice.entity.PersonalDetails;
import com.spanprints.authservice.enums.Gender;

import lombok.Getter;

@Getter
public class PersonalDetailsResponse extends EntityResponseDto {

	private String name;
	private Instant birthday;
	private Gender gender;
	private Long accountId;

	public PersonalDetailsResponse(PersonalDetails personalDetails) {
		super(personalDetails);
		this.accountId = personalDetails.getAccount() != null ? personalDetails.getAccount().getId() : null;
	}

}
