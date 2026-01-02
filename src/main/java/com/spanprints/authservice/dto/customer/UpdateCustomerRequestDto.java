package com.spanprints.authservice.dto.customer;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerRequestDto {
	@NotBlank(message = "Customer id is required")
	private Long id;
	@NotBlank(message = "Customer UUID is required")
	private String uuid;
	@NotBlank(message = "Customer name is required")
	private String name;
	private String email;
	private String primaryPhoneNumber;
	private String alternatePhoneNumber;
}
