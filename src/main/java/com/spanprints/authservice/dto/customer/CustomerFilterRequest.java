package com.spanprints.authservice.dto.customer;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerFilterRequest {

	@NotNull(message = "Page number cannot be empty")
	@Min(0)
	private Integer pageNumber;

	@NotNull(message = "Page size cannot be empty")
	@Min(1)
	private Integer pageSize;

	private String name;
	private String email;
	private String phone;
	private String address;
	private Long outstandingAmountMin;
	private Long outstandingAmountMax;
	private Long orderCountMin;
	private Long orderCountMax;
}
