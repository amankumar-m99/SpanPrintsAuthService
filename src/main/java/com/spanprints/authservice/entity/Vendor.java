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
	private String primaryPhoneNumber;
	private String alternatePhoneNumber;
	private String address;
	@CreatedDate
	private LocalDateTime dateAdded;

	@ManyToOne
	@JoinColumn(name = "added by account_id", referencedColumnName = "id")
	@JsonIgnore
	private Account addedBy;

	@JsonProperty("addedByAccountId")
	public Long getAccountId() {
		return addedBy != null ? addedBy.getId() : null;
	}

	@JsonProperty("addedBy")
	public String getAddedBy() {
		return addedBy != null ? addedBy.getUsername() : null;
	}

}
