package com.spanprints.authservice.dto.printjob;

import com.spanprints.authservice.dto.EntityResponseDto;
import com.spanprints.authservice.entity.PrintJobHistory;

import lombok.Getter;

@Getter
public class PrintJobHistoryResponse extends EntityResponseDto {

	private String description;

	private String username;
	private String userUuid;

	public PrintJobHistoryResponse(PrintJobHistory history) {
		super(history);
		this.description = history.getDescription();
		this.username = history.getAccount().getUsername();
		this.userUuid = history.getAccount().getUuid();
	}
}
