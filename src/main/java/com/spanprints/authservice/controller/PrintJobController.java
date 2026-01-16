package com.spanprints.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spanprints.authservice.dto.printjob.CreatePrintJobRequest;
import com.spanprints.authservice.dto.printjob.PrintJobResponse;
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
@RequestMapping("/printjobs")
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

	@PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public PrintJob createPrintJob(@Valid @ModelAttribute CreatePrintJobRequest request,
			@RequestParam(name = "attachments", required = false) List<MultipartFile> attachments) {
		Account account = securityUtils.getRequestingAccount();
		Customer customer = customerService.getCustomerById(request.getCustomerId());
		PrintJobType printJobType = printJobTypeService.getPrintJobTypeById(request.getPrintJobId());
		PrintJob printJob = printJobService.createPrintJob(request, printJobType, account, customer);
		ledgerService.addTransaction(printJob);
		return printJob;
	}

	@GetMapping
	public List<PrintJobResponse> getAllPrintJobs() {
		return printJobService.getAllPrintJobs().stream().map(PrintJobResponse::new).toList();
	}
}
