package com.spanprints.authservice.dto.customer;

import java.util.List;

import org.springframework.data.domain.Page;

import com.spanprints.authservice.entity.Customer;

import lombok.Getter;

@Getter
public class CustomerPaginatonResponse {

	private List<CustomerResponse> customers;
	private int currentPageNumber;
	private int numberOfTotalPages;
	private long totalElements;
	private boolean hasNext;
	private boolean hasPrevious;
	private boolean isFirst;
	private boolean isLast;
	private int size;

	public CustomerPaginatonResponse(Page<Customer> page) {
		customers = page.getContent().stream().map(CustomerResponse::new).toList();
		currentPageNumber = page.getNumber();
		numberOfTotalPages = page.getTotalPages();
		totalElements = page.getTotalElements();
		size = page.getSize();
		hasNext = page.hasNext();
		hasPrevious = page.hasPrevious();
		isFirst = page.isFirst();
		isLast = page.isLast();
	}

}
