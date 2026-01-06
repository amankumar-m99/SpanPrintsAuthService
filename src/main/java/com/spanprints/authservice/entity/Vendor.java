package com.spanprints.authservice.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Vendor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String uuid;
	private String email;
	private String name;
	private String address;
	private String primaryPhoneNumber;
	private String alternatePhoneNumber;
	@CreatedDate
	private LocalDateTime createdAt;

	@ManyToOne
	@JoinColumn(name = "created_by_account_id", referencedColumnName = "id")
	@JsonIgnore
	private Account createdBy;

	@JsonProperty("createdBy")
	public String getAddedBy() {
		return createdBy != null ? createdBy.getUsername() : null;
	}

	@JsonProperty("createdByAccountId")
	public Long getAccountId() {
		return createdBy != null ? createdBy.getId() : null;
	}
}
