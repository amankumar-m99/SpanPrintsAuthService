package com.spanprints.authservice.dto.captcha;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ReCaptchaResponse {

	private boolean success;

	@JsonAlias("challenge_ts")
	private String challengeTimestamp;

	private String hostname;

	@JsonAlias("error-codes")
	private String[] errorCodes;
}