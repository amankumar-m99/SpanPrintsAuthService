package com.spanprints.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spanprints.authservice.entity.FileAttachment;
import com.spanprints.authservice.service.FileAttachmentService;

import jakarta.validation.constraints.NotNull;

@RestController
@RequestMapping("/file-attachments")
public class FileAttachmentController {

	@Autowired
	private FileAttachmentService fileAttachmentService;

	@GetMapping("/uuid/{uuid}")
	public List<FileAttachment> getFileAttatchmentsByPrintJobUuid(@PathVariable @NotNull String uuid) {
		return fileAttachmentService.getFileAttatchmentsByPrintJobUuid(uuid);
	}

}
