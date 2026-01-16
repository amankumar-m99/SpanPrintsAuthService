package com.spanprints.authservice.dto;

import com.spanprints.authservice.entity.FileAttachment;

import lombok.Getter;

@Getter
public class FileAttachmentResponse extends EntityResponseDto {

	private Long printJobId;

	public FileAttachmentResponse(FileAttachment fileAttachment) {
		super(fileAttachment);
		this.printJobId = fileAttachment.getPrintJob() != null ? fileAttachment.getPrintJob().getId() : null;
	}
}
