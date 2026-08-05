package com.spanprints.authservice.dto.investment;

import java.math.BigDecimal;
import java.time.Instant;

import com.spanprints.authservice.dto.EntityResponseDto;
import com.spanprints.authservice.entity.Investment;

import lombok.Getter;

@Getter
public class InvestmentResponse extends EntityResponseDto {

	private BigDecimal amount;
	private String description;
	private Instant dateOfInvestment;
	private String createdBy;
	private Long createdById;
	private Long ledgerId;
	private String ledgerUuid;

	public InvestmentResponse(Investment investment) {
		super(investment);
		this.amount = investment.getAmount();
		this.description = investment.getDescription();
		this.dateOfInvestment = investment.getDateOfInvestment();
		this.createdBy = investment.getAccount() != null ? investment.getAccount().getUsername() : null;
		this.createdById = investment.getAccount() != null ? investment.getAccount().getId() : null;
		this.ledgerId = investment.getLedger() != null ? investment.getLedger().getId() : null;
		this.ledgerUuid = investment.getLedger() != null ? investment.getLedger().getUuid() : null;
	}
}
