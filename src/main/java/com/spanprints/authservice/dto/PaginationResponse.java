package com.spanprints.authservice.dto;

import java.util.List;
import java.util.function.Function;

import org.springframework.data.domain.Page;

import com.spanprints.authservice.entity.AuditableBaseEntity;

import lombok.Getter;

@Getter
public class PaginationResponse<T extends AuditableBaseEntity, U extends EntityResponseDto> {

	private List<U> elements;
	private int currentPageNumber;
	private int numberOfTotalPages;
	private long totalElements;
	private boolean hasNext;
	private boolean hasPrevious;
	private boolean isFirst;
	private boolean isLast;
	private int size;

	public PaginationResponse(Page<T> page, Function<T, U> fun) {
		elements = page.getContent().stream().map(fun).toList();
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
