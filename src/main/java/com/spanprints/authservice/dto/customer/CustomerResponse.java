package com.spanprints.authservice.dto.customer;

import java.time.Instant;
import java.util.List;

import com.spanprints.authservice.entity.Customer;
import com.spanprints.authservice.entity.PrintJob;

import io.jsonwebtoken.lang.Collections;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {

	private Long id;
	private String uuid;
	private String name;
	private String email;
	private String address;
	private String primaryPhoneNumber;
	private String alternatePhoneNumber;
	private Instant createdAt;
	private String createdBy;
	private Long createdById;
	private Instant updatedAt;
	private List<Long> printJobIds;

	public CustomerResponse(Customer customer) {
		build(customer);
	}

	private void build(Customer customer) {
		this.id = customer.getId();
		this.uuid = customer.getUuid();
		this.name = customer.getName();
		this.email = customer.getEmail();
		this.address = customer.getAddress();
		this.primaryPhoneNumber = customer.getPrimaryPhoneNumber();
		this.alternatePhoneNumber = customer.getAlternatePhoneNumber();
		this.createdAt = customer.getCreatedAt();
		this.createdBy = customer.getAccount().getUsername();
		this.createdById = customer.getAccount().getId();
		this.updatedAt = customer.getUpdatedAt();
		this.printJobIds = buildPrintJobIds(customer);
	}

	private List<Long> buildPrintJobIds(Customer customer) {
		if (customer.getPrintJobs() == null) {
			return Collections.emptyList();
		}
		return customer.getPrintJobs().stream().map(PrintJob::getId).toList();
	}
}
