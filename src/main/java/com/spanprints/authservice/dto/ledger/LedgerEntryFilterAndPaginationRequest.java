package com.spanprints.authservice.dto.ledger;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.spanprints.authservice.dto.PaginationRequest;
import com.spanprints.authservice.entity.LedgerEntrySource;
import com.spanprints.authservice.entity.LedgerEntryType;

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
	private List<LedgerEntryType> ledgerEntryTypes;
	private List<LedgerEntrySource> ledgerEntrySources;
	private BigDecimal amountMin;
	private BigDecimal amountMax;
	private LocalDate transactionDateMin;
	private LocalDate transactionDateMax;
	private String uuid;
	private String description;
}
