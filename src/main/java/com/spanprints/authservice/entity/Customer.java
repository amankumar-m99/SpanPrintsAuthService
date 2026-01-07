package com.spanprints.authservice.entity;

import java.time.Instant;
import java.util.List;

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
	private String name;
	private String email;
	private String address;
	private String primaryPhoneNumber;
	private String alternatePhoneNumber;
	@CreatedDate
	private Instant createdAt;
	private Instant updatedAt;

	@ManyToOne
	@JoinColumn(name = "account_id", referencedColumnName = "id")
	@JsonIgnore
	private Account account;

	@JsonProperty("createdBy")
	public String getAccountUsername() {
		return account != null ? account.getUsername() : null;
	}

	@JsonProperty("createdById")
	public Long getAccountId() {
		return account != null ? account.getId() : null;
	}

	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<PrintJob> printJobs;
	@JsonProperty("printJobIds")
	public List<Long> getPrintJobIds() {
		if (printJobs == null) {
			return Collections.emptyList();
		}
		return printJobs.stream().map(PrintJob::getId).toList();
	}
}
