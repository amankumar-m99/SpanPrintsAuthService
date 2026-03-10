package com.spanprints.authservice.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.spanprints.authservice.dto.captcha.ReCaptchaResponse;

@Component
public class GReCaptchaUtils {

	@Value("${spanprints.g-re-captcha.url}")
	private String gReCaptchaUrl;
	@Value("${spanprints.g-re-captcha.site-key}")
	private String gReCaptchaSiteKey;

	public void verify(String reCaptchaToken) {
		String query = "secret=" + gReCaptchaSiteKey + "&" + "response=" + reCaptchaToken;
		String finalUrl = gReCaptchaUrl + "?" + query;
		RestTemplate rt = new RestTemplate();
		ReCaptchaResponse response = rt.postForEntity(finalUrl, null, ReCaptchaResponse.class).getBody();
		if (reCaptchaToken != null && (response == null || !response.isSuccess())) {
			throw new BadCredentialsException("Invalid captcha");
		}
	}
}
