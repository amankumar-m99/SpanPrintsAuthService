package com.spanprints.authservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.PrintJob;
import com.spanprints.authservice.entity.PrintJobHistory;
import com.spanprints.authservice.exception.printjob.PrintJobNotFoundException;
import com.spanprints.authservice.repository.PrintJobHistoryRepository;
import com.spanprints.authservice.repository.PrintJobRepository;

@Service
public class PrintJobHistoryService {

	@Autowired
	private PrintJobHistoryRepository printJobHistoryRepository;
	@Autowired
	private PrintJobRepository printJobRepository;

	public PrintJobHistory createPrintJobHistory(PrintJob printJob, Account account, String description) {
		PrintJobHistory history = PrintJobHistory.builder()
				.description(description)
				.printJob(printJob)
				.account(account)
   				.build();
		return printJobHistoryRepository.save(history);
	}

	public List<PrintJobHistory> getPrintJobHistoryByPrintJobUUid(String printJobUuid) {
		PrintJob printJob = printJobRepository.findByUuid(printJobUuid).orElseThrow(()-> new PrintJobNotFoundException("No print job found by given id."));
		return printJob.getPrintJobHistories();
	}

	public List<PrintJobHistory> getAllPrintJobHistories() {
		return printJobHistoryRepository.findAll();
	}
}
