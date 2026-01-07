package com.spanprints.authservice.dto.personaldetails;

import java.time.Instant;

import com.spanprints.authservice.enums.Gender;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UpdatePersonalDetailsRequest {

	@NotBlank
	private Long accountId;
	private String name;
	private Gender gender;
	private Instant birthday;

}
