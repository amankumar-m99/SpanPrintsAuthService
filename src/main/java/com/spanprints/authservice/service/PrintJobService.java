package com.spanprints.authservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.printjob.CreatePrintJobRequest;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.Customer;
import com.spanprints.authservice.entity.PrintJob;
import com.spanprints.authservice.entity.PrintJobType;
import com.spanprints.authservice.exception.printjob.PrintJobNotFoundException;
import com.spanprints.authservice.repository.PrintJobRepository;
import com.spanprints.authservice.util.BasicUtils;

@Service
public class PrintJobService {

	@Autowired
	private PrintJobRepository printJobRepository;

	public PrintJob createPrintJob(CreatePrintJobRequest request, PrintJobType jobType, Account account,
			Customer customer) {
		PrintJob printJob = convertToPrintJobFromDto(request, jobType, account, customer);
		return printJobRepository.save(printJob);
	}

	public PrintJob getPrintJobById(Long id) {
		return printJobRepository.findById(id)
				.orElseThrow(() -> new PrintJobNotFoundException("No print job found by given id."));
	}

	public List<PrintJob> getAllPrintJobs() {
		return printJobRepository.findAll();
	}

	public PrintJob convertToPrintJobFromDto(CreatePrintJobRequest request, PrintJobType jobType, Account account,
			Customer customer) {
		return PrintJob.builder().customer(customer).account(account).jobType(jobType).count(request.getCount())
				.dateOfDelivery(BasicUtils.convertLocalDateToInstant(request.getDateOfDelivery()))
				.totalAmount(request.getTotalAmount()).depositAmount(request.getDepositAmount()).note(request.getNote())
				.bookNumber(request.getBookNumber()).wBookNumber(request.getWBookNumber())
				.description(request.getDescription()).discountedAmount(request.getDiscountedAmount())
				.pendingAmount(request.getPendingAmount()).paymentStatus(request.getPaymentStatus()).build();
	}

}
