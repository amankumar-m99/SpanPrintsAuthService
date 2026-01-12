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
import com.spanprints.authservice.entity.PrintJobType;
import com.spanprints.authservice.service.PrintJobTypeService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/printjob")
public class PrintJobTypeController {

	@Autowired
	private PrintJobTypeService printJobTypeService;

	@PostMapping("")
	public PrintJobType createPrintJobType(@Valid @RequestBody CreatePrintJobTypeRequest request) {
		return printJobTypeService.createPrintJobType(request);
	}

	@GetMapping("")
	public List<PrintJobType> getAllPrintJobs() {
		return printJobTypeService.getAllPrintJobTypes();
	}

	@GetMapping("/name/{name}")
	public PrintJobType getPrintJobTypeByName(@PathVariable("name") String name) {
		return printJobTypeService.getPrintJobTypeByName(name.toLowerCase());
	}
}
