package com.spanprints.authservice.dto.printjob;

import com.spanprints.authservice.enums.PrintJobStatus;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UpdatePrintJobStatusRequest {

	@NotNull(message = "Print Job uuid is required")
	private String orderUuid;

	@NotNull(message = "Order status cannot be empty")
	@Enumerated(EnumType.STRING)
	private PrintJobStatus printJobStatus;
}
