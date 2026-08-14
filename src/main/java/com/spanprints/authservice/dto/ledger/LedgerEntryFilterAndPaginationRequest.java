package com.spanprints.authservice.dto.ledger;

import com.spanprints.authservice.dto.PaginationRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LedgerEntryFilterAndPaginationRequest {

	private PaginationRequest paginationRequest;
}
