package com.spanprints.authservice.dto;

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
public class PaginationRequest {

	@NotNull(message = "Page number cannot be empty")
	@Min(0)
	private Integer pageNumber;

	@NotNull(message = "Page size cannot be empty")
	@Min(1)
	private Integer pageSize;
}
