package com.spanprints.authservice.dto;

import java.time.LocalDate;

import com.spanprints.authservice.enums.Gender;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersonalDetailsDto {

	private String name;
	private Gender gender;
	private LocalDate birthday;

}
