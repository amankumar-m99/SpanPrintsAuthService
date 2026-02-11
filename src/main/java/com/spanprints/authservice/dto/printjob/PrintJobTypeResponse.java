package com.spanprints.authservice.dto.printjob;

import com.spanprints.authservice.dto.EntityResponseDto;
import com.spanprints.authservice.entity.PrintJobType;

import lombok.Getter;

@Getter
public class PrintJobTypeResponse extends EntityResponseDto {

	private String jobId;
	private String name;
	private String description;

	public PrintJobTypeResponse(PrintJobType printJobType) {
		super(printJobType);
		this.jobId = printJobType.getJobId();
		this.name = printJobType.getName();
		this.description = printJobType.getDescription();
	}
}
