package com.spanprints.authservice.dto.printjob;

import java.util.List;

import org.springframework.data.domain.Page;

import com.spanprints.authservice.entity.PrintJob;

import lombok.Getter;

@Getter
public class PrintJobPaginatonResponse {

	private List<PrintJobResponse> orders;
	private int currentPageNumber;
	private int numberOfTotalPages;
	private long totalElements;
	private boolean hasNext;
	private boolean hasPrevious;
	private boolean isFirst;
	private boolean isLast;
	private int size;

	public PrintJobPaginatonResponse(Page<PrintJob> page) {
		orders = page.getContent().stream().map(PrintJobResponse::new).toList();
		currentPageNumber = page.getNumber();
		numberOfTotalPages = page.getTotalPages();
		totalElements = page.getTotalElements();
		size = page.getSize();
		hasNext = page.hasNext();
		hasPrevious = page.hasPrevious();
		isFirst = page.isFirst();
		isLast = page.isLast();
	}

}
