package com.spanprints.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spanprints.authservice.dto.printjob.CreatePrintJobRequest;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.Customer;
import com.spanprints.authservice.entity.PrintJob;
import com.spanprints.authservice.entity.PrintJobType;
import com.spanprints.authservice.service.CustomerService;
import com.spanprints.authservice.service.LedgerService;
import com.spanprints.authservice.service.PrintJobService;
import com.spanprints.authservice.service.PrintJobTypeService;
import com.spanprints.authservice.util.SecurityUtils;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/printjob")
public class PrintJobController {

	@Autowired
	private PrintJobService printJobService;
	@Autowired
	private PrintJobTypeService printJobTypeService;
	@Autowired
	private LedgerService ledgerService;
	@Autowired
	private SecurityUtils securityUtils;
	@Autowired
	private CustomerService customerService;

	@PostMapping("")
	public PrintJob createPrintJob(@Valid @RequestBody CreatePrintJobRequest request) {
		Account account = securityUtils.getRequestingAccount();
		Customer customer = customerService.getCustomerById(request.getCustomerId());
		PrintJobType printJobType = printJobTypeService.getPrintJobTypeById(request.getPrintJobId());
		PrintJob printJob = printJobService.createPrintJob(request, printJobType, account, customer);
		ledgerService.addTransaction(printJob);
		return printJob;
	}

	@GetMapping("")
	public List<PrintJob> getAllPrintJobs() {
		return printJobService.getAllPrintJobs();
	}
}
