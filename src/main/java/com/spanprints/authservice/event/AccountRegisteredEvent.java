package com.spanprints.authservice.event;

import com.spanprints.authservice.dto.TokenResponseDto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AccountRegisteredEvent {

	private String email;
	private String username;
	private TokenResponseDto tokenResponseDto;
}
