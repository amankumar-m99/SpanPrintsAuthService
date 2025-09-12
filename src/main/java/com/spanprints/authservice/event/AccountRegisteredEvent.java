package com.spanprints.authservice.event;

import com.spanprints.authservice.dto.TokenResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountRegisteredEvent {

	private String email;
	private String username;
	private TokenResponseDto tokenResponseDto;

}
