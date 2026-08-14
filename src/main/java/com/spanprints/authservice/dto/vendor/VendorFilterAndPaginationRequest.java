package com.spanprints.authservice.dto.vendor;

import com.spanprints.authservice.dto.PaginationRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VendorFilterAndPaginationRequest {

	private PaginationRequest paginationRequest;
}
