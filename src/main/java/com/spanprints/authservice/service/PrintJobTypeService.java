package com.spanprints.authservice.service;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.printjob.CreatePrintJobTypeRequest;
import com.spanprints.authservice.entity.PrintJobType;
import com.spanprints.authservice.exception.printjob.PrintJobTypeAlreadyExistsException;
import com.spanprints.authservice.exception.printjob.PrintJobTypeNotFoundException;
import com.spanprints.authservice.repository.PrintJobTypeRepository;
import com.spanprints.authservice.util.BasicUtils;

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

	public PrintJobType convertToPrintJobTypeFromDto(CreatePrintJobTypeRequest request) {
		return PrintJobType.builder().id(null).name(BasicUtils.formatStringToTitle(request.getName()))
				.description(request.getDescription()).createdAt(Instant.now()).updatedAt(Instant.now()).build();
	}

}
