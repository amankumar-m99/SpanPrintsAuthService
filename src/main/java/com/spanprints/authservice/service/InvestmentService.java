package com.spanprints.authservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.investment.CreateInvestmentRequest;
import com.spanprints.authservice.dto.investment.UpdateInvestmentRequest;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.Investment;
import com.spanprints.authservice.exception.InvalidInputsException;
import com.spanprints.authservice.exception.ledger.TransactionNotFoundException;
import com.spanprints.authservice.repository.InvestmentRepository;
import com.spanprints.authservice.util.BasicUtils;

@Service
public class InvestmentService {

	@Autowired
	private InvestmentRepository investmentRepository;

	public Investment createInvestment(CreateInvestmentRequest request, Account account) {
		Investment investment = Investment.builder().amount(request.getAmount()).description(request.getDescription())
				.dateOfInvestment(BasicUtils.convertLocalDateToInstant(request.getDateOfInvestment())).account(account)
				.build();
		return investmentRepository.save(investment);
	}

	public List<Investment> getAllInvestments() {
		return investmentRepository.findAll();
	}

	public Investment getInvestmentById(Long id) {
		return investmentRepository.findById(id)
				.orElseThrow(() -> new TransactionNotFoundException("No investment found by given id."));
	}

	public Investment getInvestmentByUuid(String uuid) {
		return investmentRepository.findByUuid(uuid)
				.orElseThrow(() -> new TransactionNotFoundException("No investment found by given uuid."));
	}

	public Investment updateInvestment(UpdateInvestmentRequest request) {
		Investment investment = null;
		if (!BasicUtils.isNullOrBlank(request.getUuid())) {
			investment = getInvestmentByUuid(request.getUuid());
		} else if (request.getId() != null) {
			investment = getInvestmentById(request.getId());
		} else {
			throw new InvalidInputsException("Provide id or uuid");
		}
		return updateInvestment(investment, request);
	}

	private Investment updateInvestment(Investment investment, UpdateInvestmentRequest request) {
		investment.setAmount(request.getAmount());
		investment.setDescription(request.getDescription());
		investment.setDateOfInvestment(BasicUtils.convertLocalDateToInstant(request.getDateOfInvestment()));
		return investmentRepository.save(investment);
	}
}
