package com.spanprints.authservice.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spanprints.authservice.dto.ProfilePicResource;
import com.spanprints.authservice.dto.ProfilePicResponse;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.ProfilePic;
import com.spanprints.authservice.exception.profilepic.ProfilePicFormatNotAcceptedException;
import com.spanprints.authservice.service.AccountService;
import com.spanprints.authservice.service.ProfilePicService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/profile-pics")
public class ProfilePicController {

	@Autowired
	private ProfilePicService profilePicService;
	@Autowired
	private AccountService accountService;

	@GetMapping(value = "/id/{id}", produces = { MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE })
	public void getUserProfilePic(@PathVariable("id") String uuid, HttpServletResponse response) throws IOException {
		Account account = accountService.getAccountByUuid(uuid);
		ProfilePicResource resource = this.profilePicService.getProfilePic(account);
		response.setContentType(resource.getContentType());
		StreamUtils.copy(resource.getFileInputStream(), response.getOutputStream());
	}

	@PostMapping
	public ResponseEntity<ProfilePicResponse> saveUserProfilePic(HttpServletRequest request,
			@RequestParam("profile-pic") MultipartFile multipartFile,
			@AuthenticationPrincipal UserDetails userDetails) {
//		String type = multipartFile.getContentType().toLowerCase();
		String type = multipartFile.getOriginalFilename().toLowerCase();
		if (!(type.endsWith("jpeg") || type.endsWith("jpg") || type.endsWith("png"))) {
			throw new ProfilePicFormatNotAcceptedException("Only jpeg and png files are accepted.");
		}
		Account account = accountService.getAccountByUsername(userDetails.getUsername());
		ProfilePic profilePic = this.profilePicService.updateProfilePic(account, multipartFile);
		return new ResponseEntity<>(new ProfilePicResponse(profilePic), HttpStatus.OK);
	}

	@DeleteMapping
	public void deleteProfilePic(@AuthenticationPrincipal UserDetails userDetails) {
		Account account = accountService.getAccountByUsername(userDetails.getUsername());
		this.profilePicService.deleteProfilePic(account);
	}
}
