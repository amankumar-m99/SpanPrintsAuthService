package com.spanprints.authservice.dto.customer;

import java.util.List;

import com.spanprints.authservice.dto.EntityResponseDto;
import com.spanprints.authservice.entity.Customer;
import com.spanprints.authservice.entity.PrintJob;

import io.jsonwebtoken.lang.Collections;
import lombok.Getter;

@Getter
public class CustomerResponse extends EntityResponseDto {

	private String name;
	private String email;
	private String address;
	private String primaryPhoneNumber;
	private String alternatePhoneNumber;
	private String createdBy;
	private Long createdById;
	private List<Long> printJobIds;

	public CustomerResponse(Customer customer) {
		super(customer);
		this.name = customer.getName();
		this.email = customer.getEmail();
		this.address = customer.getAddress();
		this.primaryPhoneNumber = customer.getPrimaryPhoneNumber();
		this.alternatePhoneNumber = customer.getAlternatePhoneNumber();
		this.createdBy = customer.getAccount().getUsername();
		this.createdById = customer.getAccount().getId();
		this.printJobIds = customer.getPrintJobs() != null
				? customer.getPrintJobs().stream().map(PrintJob::getId).toList()
				: Collections.emptyList();
	}
}
