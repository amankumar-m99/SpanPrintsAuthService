package com.spanprints.authservice.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spanprints.authservice.dto.profile.ProfilePicResource;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.ProfilePic;
import com.spanprints.authservice.exception.profilepic.ProfilePicNotFoundException;
import com.spanprints.authservice.repository.ProfilePicRepository;

@Service
public class ProfilePicService {

	@Value("${spanprints.data-directory.profile-pic}")
	private String profilePicsDirectory;

	@Autowired
	private ProfilePicRepository profilePicRepository;

	public ProfilePic updateProfilePic(Account account, MultipartFile multipartFile) {
		String prefix = "/profile-pic/";
		File file = new File(profilePicsDirectory);
		if (!file.exists())
			file.mkdirs();
		try {
			ProfilePic profilePic = account.getProfilePic();
			if (profilePic != null) {
				String newFileName = updateProfilePicOnFileSystem(multipartFile, profilePic);
				profilePic.setName(newFileName);
			} else {
				String fileName = createProfilePicOnFileSystem(multipartFile);
				profilePic = ProfilePic.builder().name(fileName).account(account).build();
			}
			profilePic.setUrl(prefix + account.getUuid());
			return profilePicRepository.save(profilePic);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String updateProfilePicOnFileSystem(MultipartFile multipartFile, ProfilePic profilePic) throws IOException {
		String oldFileName = profilePic.getName();
		File oldFile = new File(profilePicsDirectory + File.separator + oldFileName);
		if (!oldFile.exists()) {
			return createProfilePicOnFileSystem(multipartFile);
		}
		String newFileName = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename().replace(" ", "_");
		extratctMultipartData(multipartFile, oldFile, null);
		boolean isSuccess = oldFile.renameTo(new File(profilePicsDirectory + File.separator + newFileName));
		return (isSuccess) ? newFileName : oldFileName;
	}

	private String createProfilePicOnFileSystem(MultipartFile multipartFile) throws IOException {
		String targetFileName = System.currentTimeMillis() + "_"
				+ multipartFile.getOriginalFilename().replace(" ", "_");
		extratctMultipartData(multipartFile, null, profilePicsDirectory + File.separator + targetFileName);
		return targetFileName;
	}

	private void extratctMultipartData(MultipartFile multipartFile, File targetFile, String targetFilePath)
			throws IOException {
		InputStream inputStream = multipartFile.getInputStream();
		byte data[] = new byte[inputStream.available()];
		inputStream.read(data);
		FileOutputStream fileOutputStream;
		if (targetFile != null && targetFile.exists()) {
			fileOutputStream = new FileOutputStream(targetFile);
		} else {
			fileOutputStream = new FileOutputStream(targetFilePath);
		}
		fileOutputStream.write(data);
		fileOutputStream.flush();
		fileOutputStream.close();
		inputStream.close();
	}

	public ProfilePicResource getProfilePicByAccount(Account account) {
		ProfilePic profilePic = account.getProfilePic();
		File file = null;
		if (profilePic != null) {
			file = new File(profilePicsDirectory + File.separator + profilePic.getName());
		}
		if (file == null || !file.exists()) {
			throw new ProfilePicNotFoundException("Profile pic not found");
		}
		return new ProfilePicResource(file);
	}

	public ProfilePicResource getProfilePicByUuid(String uuid) {
		ProfilePic profilePic = profilePicRepository.findByUuid(uuid).orElse(null);
		File file = null;
		if (profilePic != null) {
			file = new File(profilePicsDirectory + File.separator + profilePic.getName());
		}
		if (file == null || !file.exists()) {
			throw new ProfilePicNotFoundException("Profile pic not found");
		}
		return new ProfilePicResource(file);
	}

	public void deleteProfilePic(Account account) {
		ProfilePic profilePic = account.getProfilePic();
		if (profilePic != null) {
			deleteProfilePicFromDirectory(profilePic.getName());
			profilePicRepository.delete(profilePic);
		}
	}

	private void deleteProfilePicFromDirectory(String fileName) {
		File file = new File(profilePicsDirectory + File.separator + fileName);
		if (file.exists()) {
			file.delete();
		}
	}
}
