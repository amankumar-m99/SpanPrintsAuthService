package com.spanprints.authservice.dto.printjob;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreatePrintJobTypeRequest {

	@NotNull(message = "Job id cannot be empty")
	@NotBlank(message = "Job id cannot be empty")
	private String jobId;
	@NotNull(message = "Job type name cannot be empty")
	@NotBlank(message = "Job type name cannot be empty")
	private String name;
	private String description;
}
