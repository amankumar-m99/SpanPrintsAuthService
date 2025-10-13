package com.spanprints.authservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.printjob.PrintJobRequestDto;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.PrintJob;
import com.spanprints.authservice.exception.printjob.PrintJobNotFoundException;
import com.spanprints.authservice.repository.PrintJobRepository;

@Service
public class PrintJobService {

	@Autowired
	private PrintJobRepository printJobRepository;

	public PrintJob addPrintJob(PrintJobRequestDto dto, Account account) {
		PrintJob printJob = convertToPrintJobFromDto(dto);
		return printJobRepository.save(printJob);
	}

	public PrintJob getExpenseById(Long id) {
		return printJobRepository.findById(id).orElseThrow(() -> new PrintJobNotFoundException("No expense found by given id."));
	}

	public List<PrintJob> getAllPrintJobs(){
		return printJobRepository.findAll();
	}

	public PrintJobRequestDto convertToDtoJobFromPrint(PrintJob d) {
		PrintJobRequestDto p = PrintJobRequestDto.builder()
		.customerName(d.getCustomerName())
		.phone(d.getPhone())
		.address(d.getAddress())
		.jobType(d.getJobType())
		.count(d.getCount())
		.dateOfDelivery(d.getDateOfDelivery())
		.totalAmount(d.getTotalAmount())
		.depositAmount(d.getDepositAmount())
		.note(d.getNote())
		.bookNumber(d.getBookNumber())
		.wBookNumber(d.getWBookNumber())
		.description(d.getDescription())
		.discountedAmount(d.getDiscountedAmount())
		.pendingAmount(d.getPendingAmount())
		.paymentStatus(d.getPaymentStatus())
		.build();
		return p;
	}

	public PrintJob convertToPrintJobFromDto(PrintJobRequestDto d) {
		PrintJob p = PrintJob.builder()
		.customerName(d.getCustomerName())
		.phone(d.getPhone())
		.address(d.getAddress())
		.jobType(d.getJobType())
		.count(d.getCount())
		.dateOfDelivery(d.getDateOfDelivery())
		.totalAmount(d.getTotalAmount())
		.depositAmount(d.getDepositAmount())
		.note(d.getNote())
		.bookNumber(d.getBookNumber())
		.wBookNumber(d.getWBookNumber())
		.description(d.getDescription())
		.discountedAmount(d.getDiscountedAmount())
		.pendingAmount(d.getPendingAmount())
		.paymentStatus(d.getPaymentStatus())
		.build();
		return p;
	}

}
