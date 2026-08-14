package com.spanprints.authservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.printjob.CreatePrintJobTypeRequest;
import com.spanprints.authservice.dto.printjob.PrintJobTypeFilterAndPaginationRequest;
import com.spanprints.authservice.entity.PrintJobType;
import com.spanprints.authservice.exception.printjob.PrintJobTypeAlreadyExistsException;
import com.spanprints.authservice.exception.printjob.PrintJobTypeNotFoundException;
import com.spanprints.authservice.repository.PrintJobTypeRepository;
import com.spanprints.authservice.util.BasicUtils;

import jakarta.validation.Valid;

@Service
public class PrintJobTypeService {

	@Autowired
	private PrintJobTypeRepository printJobTypeRepository;

	public PrintJobType createPrintJobType(CreatePrintJobTypeRequest request) {
		throwIfAlreadyExists(request.getName());
		PrintJobType printJobType = convertToPrintJobTypeFromDto(request);
		return printJobTypeRepository.save(printJobType);
	}

	private void throwIfAlreadyExists(String name) {
		if (printJobTypeRepository.findByName(name.toLowerCase()).isPresent()) {
			throw new PrintJobTypeAlreadyExistsException("Print Job already exists by this name");
		}
	}

	public PrintJobType getPrintJobTypeById(Long id) {
		return printJobTypeRepository.findById(id)
				.orElseThrow(() -> new PrintJobTypeNotFoundException("No print job found by given id."));
	}

	public PrintJobType getPrintJobTypeByName(String name) {
		return printJobTypeRepository.findByName(name)
				.orElseThrow(() -> new PrintJobTypeNotFoundException("No print job found by given id."));
	}

	public List<PrintJobType> getAllPrintJobTypes() {
		return printJobTypeRepository.findAll();
	}

	public Page<PrintJobType> getFilteredPaginatedExpenses(@Valid PrintJobTypeFilterAndPaginationRequest filter) {
		Pageable pageable = PageRequest.of(filter.getPaginationRequest().getPageNumber(),
				filter.getPaginationRequest().getPageSize());
		return printJobTypeRepository.findAll(pageable);
	}

	public PrintJobType convertToPrintJobTypeFromDto(CreatePrintJobTypeRequest request) {
		return PrintJobType.builder().code(request.getCode()).name(BasicUtils.formatStringToTitle(request.getName()))
				.description(request.getDescription()).build();
	}
}
