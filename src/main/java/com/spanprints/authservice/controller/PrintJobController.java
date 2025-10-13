package com.spanprints.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spanprints.authservice.dto.printjob.PrintJobRequestDto;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.PrintJob;
import com.spanprints.authservice.service.LedgerService;
import com.spanprints.authservice.service.PrintJobService;
import com.spanprints.authservice.util.SecurityUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/printjob")
public class PrintJobController {

	@Autowired
	private PrintJobService printJobService;
	@Autowired
	private LedgerService ledgerService;
	@Autowired
	private SecurityUtils securityUtils;

	@PostMapping("")
	public PrintJob addExpense(@Valid @RequestBody PrintJobRequestDto dto) {
		Account account = securityUtils.getActiveAccount();
		PrintJob printJob = printJobService.addPrintJob(dto, account);
		ledgerService.addTransaction(printJob);
		return printJob;
	}

	@GetMapping("")
	public List<PrintJob> getAllPrintJobs() {
		return printJobService.getAllPrintJobs();
	}
}
