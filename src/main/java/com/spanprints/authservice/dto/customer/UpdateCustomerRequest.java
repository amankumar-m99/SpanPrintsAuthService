package com.spanprints.authservice.dto.customer;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomerRequest {

	@NotBlank(message = "Customer name is required")
	private String name;
	private String email;
	private String address;
	@NotBlank(message = "Primary phone number is required")
	private String primaryPhoneNumber;
	private String alternatePhoneNumber;

}
