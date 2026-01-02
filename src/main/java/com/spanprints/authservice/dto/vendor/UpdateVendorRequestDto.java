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
public class UpdateVendorRequestDto {
	@NotBlank(message = "Vendor id is required")
	private Long id;
	@NotBlank(message = "Customer UUID is required")
	private String uuid;
	@NotBlank(message = "Customer name is required")
	private String name;
	private String email;
	private String primaryPhoneNumber;
	private String alternatePhoneNumber;
	private String address;
}
