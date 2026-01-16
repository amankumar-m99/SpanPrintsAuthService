package com.spanprints.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spanprints.authservice.dto.printjob.CreatePrintJobTypeRequest;
import com.spanprints.authservice.dto.printjob.PrintJobTypeResponse;
import com.spanprints.authservice.service.PrintJobTypeService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/printjobtype")
public class PrintJobTypeController {

	@Autowired
	private PrintJobTypeService printJobTypeService;

	@PostMapping
	public PrintJobTypeResponse createPrintJobType(@Valid @RequestBody CreatePrintJobTypeRequest request) {
		return new PrintJobTypeResponse(printJobTypeService.createPrintJobType(request));
	}

	@GetMapping
	public List<PrintJobTypeResponse> getAllPrintJobs() {
		return printJobTypeService.getAllPrintJobTypes().stream().map(PrintJobTypeResponse::new).toList();
	}

	@GetMapping("/id/{id}")
	public PrintJobTypeResponse getPrintJobTypeById(@PathVariable @NotNull @Positive @Min(1) String name) {
		return new PrintJobTypeResponse(printJobTypeService.getPrintJobTypeByName(name.toLowerCase()));
	}

	@GetMapping("/name/{name}")
	public PrintJobTypeResponse getPrintJobTypeByName(@PathVariable @NotNull String name) {
		return new PrintJobTypeResponse(printJobTypeService.getPrintJobTypeByName(name.toLowerCase()));
	}
}
