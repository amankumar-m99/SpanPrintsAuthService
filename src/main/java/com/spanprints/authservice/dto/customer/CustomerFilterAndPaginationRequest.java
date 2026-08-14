package com.spanprints.authservice.dto.customer;

import com.spanprints.authservice.dto.PaginationRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerFilterAndPaginationRequest {

	private PaginationRequest paginationRequest;
	private String name;
	private String email;
	private String phone;
	private String address;
	private Long outstandingAmountMin;
	private Long outstandingAmountMax;
	private Long orderCountMin;
	private Long orderCountMax;
}
