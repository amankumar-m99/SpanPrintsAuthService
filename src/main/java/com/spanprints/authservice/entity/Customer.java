package com.spanprints.authservice.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.jsonwebtoken.lang.Collections;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
public class Customer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String uuid;
	private String email;
	private String name;
	private String primaryPhoneNumber;
	private String alternatePhoneNumber;
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

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<PrintJob> printJobs;

	@JsonProperty("printJobIds")
	public List<Long> getPrintJobIds() {
		if (printJobs == null) {
			return Collections.emptyList();
		}
		List<Long> ids = printJobs.stream().map(o -> o.getId()).collect(Collectors.toList());
		return ids;
	}
}
