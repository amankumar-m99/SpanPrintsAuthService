package com.spanprints.authservice.service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.printjob.CreatePrintJobRequest;
import com.spanprints.authservice.dto.printjob.PrintJobPaginatonResponse;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.Customer;
import com.spanprints.authservice.entity.PrintJob;
import com.spanprints.authservice.entity.PrintJobType;
import com.spanprints.authservice.enums.PrintJobStatus;
import com.spanprints.authservice.exception.printjob.PrintJobNotFoundException;
import com.spanprints.authservice.repository.CustomerRepository;
import com.spanprints.authservice.repository.PrintJobRepository;
import com.spanprints.authservice.util.BasicUtils;

import io.jsonwebtoken.lang.Collections;

@Service
public class PrintJobService {

	@Autowired
	private PrintJobRepository printJobRepository;

	@Autowired
	private CustomerRepository customerRepository;

	private final ZoneId zoneId = ZoneId.of("Asia/Kolkata"); // pick your app's reference timezone

	public PrintJob createPrintJob(CreatePrintJobRequest request, PrintJobType jobType, Account account,
			Customer customer) {
		PrintJob printJob = convertToPrintJobFromDto(request, jobType, account, customer);
		return printJobRepository.save(printJob);
	}

	public PrintJob getPrintJobById(Long id) {
		return printJobRepository.findById(id)
				.orElseThrow(() -> new PrintJobNotFoundException("No print job found by given id."));
	}

	public PrintJob getPrintJobByUuid(String uuid) {
		return printJobRepository.findByUuid(uuid)
				.orElseThrow(() -> new PrintJobNotFoundException("No print job found by given uuid."));
	}

	public List<PrintJob> getAllPrintJobsPlacedToday() {
		LocalDate today = LocalDate.now(zoneId);
		Instant startOfDay = today.atStartOfDay(zoneId).toInstant();
		Instant startOfNextDay = today.plusDays(1).atStartOfDay(zoneId).toInstant();
		return printJobRepository.findByDateOfPlacedGreaterThanEqualAndCreatedAtLessThan(startOfDay, startOfNextDay);
	}

	public List<PrintJob> getAllPrintJobs() {
		return printJobRepository.findAll();
	}

	public PrintJobPaginatonResponse getAllPrintJobsPaginated(int pageNumber, int pageSize) {
		Pageable page = PageRequest.of(pageNumber, pageSize);
		Page<PrintJob> all = printJobRepository.findAll(page);
		return new PrintJobPaginatonResponse(all);
	}

	public List<PrintJob> getAllPrintJobsByCustomerUuid(String uuid) {
		Optional<Customer> optional = customerRepository.findByUuid(uuid);
		if (optional.isPresent()) {
			Customer customer = optional.get();
			return customer.getPrintJobs();
		}
		return Collections.emptyList();
	}

	public List<PrintJob> findPreparedAndDueTodayOrEarlier() {
		LocalDate today = LocalDate.now(zoneId);
		Instant startOfTomorrow = today.plusDays(1).atStartOfDay(zoneId).toInstant();
		return printJobRepository.findByPrintJobStatusAndDateOfDeliveryLessThan(PrintJobStatus.PREPARED,
				startOfTomorrow);
	}

	public List<PrintJob> findByDateOfDeliveryTomorrow() {
		LocalDate tomorrow = LocalDate.now(zoneId).plusDays(1);
		return findByDateOfDelivery(tomorrow);
	}

	public List<PrintJob> findByDateOfDeliveryToday() {
		LocalDate today = LocalDate.now(zoneId);
		return findByDateOfDelivery(today);
	}

	private List<PrintJob> findByDateOfDelivery(LocalDate day) {
		Instant startOfDay = day.atStartOfDay(zoneId).toInstant();
		Instant startOfDayAfter = day.plusDays(1).atStartOfDay(zoneId).toInstant();
		return printJobRepository.findByDateOfDeliveryBetween(startOfDay, startOfDayAfter);
	}

	public PrintJob convertToPrintJobFromDto(CreatePrintJobRequest request, PrintJobType jobType, Account account,
			Customer customer) {
		return PrintJob.builder().customer(customer).account(account).jobType(jobType).quantity(request.getQuantity())
				.dateOfDelivery(BasicUtils.convertLocalDateToInstant(request.getDateOfDelivery()))
				.dateOfPlaced(BasicUtils.convertLocalDateToInstant(request.getDateOfPlaced()))
				.printJobStatus(PrintJobStatus.PLACED).totalAmount(request.getTotalAmount())
				.depositAmount(request.getDepositAmount()).note(request.getNote())
				.bookNumber(BasicUtils.parserStringToInteger(request.getBookNumber()))
//				.wBookNumber(BasicUtils.parserStringToInteger(request.getWBookNumber()))
				.description(request.getDescription()).discountedAmount(request.getDiscountedAmount())
				.pendingAmount(request.getPendingAmount()).paymentStatus(request.getPaymentStatus()).build();
	}

}
