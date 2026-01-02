package com.spanprints.authservice.dto.vendor;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddVendorRequestDto {
	@NotBlank(message = "Vendor name is required")
	private String name;
	private String email;
	private String primaryPhoneNumber;
	private String alternatePhoneNumber;
	private String address;
}
