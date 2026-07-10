package com.spanprints.authservice.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import com.spanprints.authservice.entity.FileAttachment;
import com.spanprints.authservice.entity.PrintJob;
import com.spanprints.authservice.repository.FileAttachmentRepository;
import com.spanprints.authservice.repository.PrintJobRepository;
import com.spanprints.authservice.util.BasicUtils;

@Service
public class FileAttachmentService {

	@Value("${spanprints.data-directory.file-attachment}")
	private String fileAttachmentDirectory;

	@Autowired
	private FileAttachmentRepository fileAttachmentRepository;

	@Autowired
	private PrintJobRepository printJobRepository;

	public List<FileAttachment> addFileAttachment(List<MultipartFile> attachments, PrintJob printJob) {
		File file = new File(fileAttachmentDirectory);
		if (!file.exists())
			file.mkdirs();
		List<FileAttachment> fileAttachments = new ArrayList<>();
		try {
			for (MultipartFile multipartFile : attachments) {
				String createdFileName = createProfilePicOnFileSystem(multipartFile);
				long size = multipartFile.getSize();
				String[] strings = BasicUtils.separateFileExtension(multipartFile.getOriginalFilename());
				String originalFileName = strings[0];
				String fileExt = strings[1];
				FileAttachment fileAttachment = FileAttachment.builder().originalFileName(originalFileName)
						.createdFileName(createdFileName).fileType(fileExt).contentType(multipartFile.getContentType())
						.size(size).build();
				fileAttachment.setPrintJob(printJob);
				fileAttachments.add(fileAttachment);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileAttachmentRepository.saveAll(fileAttachments);
	}

	private String createProfilePicOnFileSystem(MultipartFile multipartFile) throws IOException {
		String targetFileName = System.currentTimeMillis() + "_"
				+ multipartFile.getOriginalFilename().replace(" ", "_");
		extratctMultipartData(multipartFile, fileAttachmentDirectory + File.separator + targetFileName);
		return targetFileName;
	}

	private void extratctMultipartData(MultipartFile multipartFile, String targetFilePath) throws IOException {
		InputStream inputStream = multipartFile.getInputStream();
		byte[] data = new byte[inputStream.available()];
		inputStream.read(data);
		FileOutputStream fileOutputStream = new FileOutputStream(targetFilePath);
		fileOutputStream.write(data);
		fileOutputStream.flush();
		fileOutputStream.close();
		inputStream.close();
	}

	public List<FileAttachment> getFileAttatchmentsByPrintJobUuid(String uuid) {
		Optional<PrintJob> byUuid = printJobRepository.findByUuid(uuid);
		if (byUuid.isEmpty()) {
			return Collections.emptyList();
		}
		PrintJob printJob = byUuid.get();
		return printJob.getAttachments();
	}

	public ResponseEntity<Resource> downloadFile(@PathVariable String uuid) {
		return fetchFile(uuid, "attachment");
	}

	public ResponseEntity<Resource> previewFile(@PathVariable String uuid) {
		return fetchFile(uuid, "inline");
	}

	private ResponseEntity<Resource> fetchFile(String uuid, String contentDisposition) {
		try {
			FileAttachment fileAttachment = fileAttachmentRepository.findByUuid(uuid).orElse(null);
			if (fileAttachment == null) {
				return ResponseEntity.notFound().build();
			}
			String fileName = fileAttachment.getCreatedFileName();
			Path fileStorageLocation = Paths.get(fileAttachmentDirectory);
			Path filePath = fileStorageLocation.resolve(fileName).normalize();

			// Security: prevent path traversal
			if (!filePath.startsWith(fileStorageLocation)) {
				return ResponseEntity.badRequest().build();
			}

			Resource resource = new UrlResource(filePath.toUri());

			if (!resource.exists() || !resource.isReadable()) {
				return ResponseEntity.notFound().build();
			}

//			String contentType = Files.probeContentType(filePath);
			String contentType = fileAttachment.getContentType();
			if (contentType == null) {
				contentType = Files.probeContentType(filePath);
			}
			if (contentType == null) {
				contentType = "application/octet-stream";
			}

//			String downloadFileName = resource.getFilename();
			String downloadFileName = fileAttachment.getOriginalFileName();
			String contentDispositionStr = String.format("%s; filename=\"%s\"", contentDisposition, downloadFileName);
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
					.header(HttpHeaders.CONTENT_DISPOSITION, contentDispositionStr).body(resource);

		} catch (MalformedURLException e) {
			return ResponseEntity.badRequest().build();
		} catch (IOException e) {
			return ResponseEntity.internalServerError().build();
		}
	}
}
