package com.spanprints.authservice.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spanprints.authservice.entity.FileAttachment;
import com.spanprints.authservice.entity.PrintJob;
import com.spanprints.authservice.repository.FileAttachmentRepository;
import com.spanprints.authservice.util.BasicUtils;

@Service
public class FileAttachmentService {

	@Value("${spanprints.data-directory.file-attachment}")
	private String fileAttachmentDirectory;

	@Autowired
	private FileAttachmentRepository fileAttachmentRepository;
	
	public List<FileAttachment> addFileAttachment(List<MultipartFile> attachments, PrintJob printJob) {
		File file = new File(fileAttachmentDirectory);
		if (!file.exists())
			file.mkdirs();
		List<FileAttachment> fileAttachments = new ArrayList<>();
		try {
			for(MultipartFile multipartFile: attachments) {
				String createdFileName = createProfilePicOnFileSystem(multipartFile);
				String[] strings = BasicUtils.separateFileExtension(multipartFile.getOriginalFilename());
				String originalFileName = strings[0];
				String fileExt = strings[1];
				FileAttachment fileAttachment = FileAttachment.builder()
						.originalFileName(originalFileName)
						.createdFileName(createdFileName)
						.fileType(fileExt)
						.build();
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

	private void extratctMultipartData(MultipartFile multipartFile, String targetFilePath)
			throws IOException {
		InputStream inputStream = multipartFile.getInputStream();
		byte[] data = new byte[inputStream.available()];
		inputStream.read(data);
		FileOutputStream fileOutputStream = new FileOutputStream(targetFilePath);
		fileOutputStream.write(data);
		fileOutputStream.flush();
		fileOutputStream.close();
		inputStream.close();
	}

}
