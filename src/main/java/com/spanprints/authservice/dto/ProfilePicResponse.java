package com.spanprints.authservice.dto;

import com.spanprints.authservice.entity.ProfilePic;

import lombok.Getter;

@Getter
public class ProfilePicResponse extends EntityResponseDto {

	private String name;
	private String url;
	private Long accountId;

	public ProfilePicResponse(ProfilePic profilePic) {
		super(profilePic);
		this.name = profilePic.getName();
		this.accountId = profilePic.getAccount() != null ? profilePic.getAccount().getId() : null;
	}
}
