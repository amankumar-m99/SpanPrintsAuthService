package com.spanprints.authservice.dto.personaldetails;

import java.time.Instant;

import com.spanprints.authservice.enums.Gender;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CreatePersonalDetailsRequest {

	private String name;
	private Gender gender;
	private Instant birthday;

}
