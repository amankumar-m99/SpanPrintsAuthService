package com.spanprints.authservice.specifications;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.spanprints.authservice.dto.ledger.LedgerEntryFilterAndPaginationRequest;
import com.spanprints.authservice.entity.LedgerEntry;
import com.spanprints.authservice.exception.InvalidInputsException;
import com.spanprints.authservice.util.BasicUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class LedgderSpecifications {

	private LedgderSpecifications() {
	}

	public static Specification<LedgerEntry> withFilter(LedgerEntryFilterAndPaginationRequest filter) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (!BasicUtils.isNullOrBlank(filter.getDescription())) {
				String searchPattern = "%" + filter.getDescription().trim().toLowerCase() + "%";
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), searchPattern));
			}

			if (!BasicUtils.isNullOrBlank(filter.getUuid())) {
				String searchPattern = "%" + filter.getUuid().trim().toLowerCase() + "%";
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("uuid")), searchPattern));
			}

			if (filter.getLedgerEntrySources() != null && !filter.getLedgerEntrySources().isEmpty()) {
				Expression<LedgerEntry> expenseTypeExpression = root.get("ledgerEntrySource");
				predicates.add(expenseTypeExpression.in(filter.getLedgerEntrySources()));
			}

			if (filter.getLedgerEntryTypes() != null && !filter.getLedgerEntryTypes().isEmpty()) {
				Expression<LedgerEntry> expenseTypeExpression = root.get("ledgerEntryType");
				predicates.add(expenseTypeExpression.in(filter.getLedgerEntryTypes()));
			}

			Predicate dateOfTransactionCriteria = buildCriteriaForCol(criteriaBuilder, root, "dateOfExpense",
					filter.getTransactionDateMin(), filter.getTransactionDateMax());
			if (dateOfTransactionCriteria != null)
				predicates.add(dateOfTransactionCriteria);

			Predicate amountCriteria = buildCriteriaForCol(criteriaBuilder, root, "amount", filter.getAmountMin(),
					filter.getAmountMax());
			if (amountCriteria != null)
				predicates.add(amountCriteria);

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}

	private static Predicate buildCriteriaForCol(CriteriaBuilder criteriaBuilder, Root<LedgerEntry> root,
			String colName, BigDecimal min, BigDecimal max) {
		if (min == null && max == null) {
			return null;
		}
		if (min != null && max != null) {
			if (min.compareTo(max) > 0) {
				throw new InvalidInputsException(
						"Invalid values of min & max " + colName + ". Min must be less than max");
			} else {
				return criteriaBuilder.between(root.get(colName), min, max);
			}
		} else if (min != null) {
			return criteriaBuilder.greaterThanOrEqualTo(root.get(colName), min);
		} else if (max != null) {
			return criteriaBuilder.lessThanOrEqualTo(root.get(colName), max);
		}
		return null;
	}

	private static Predicate buildCriteriaForCol(CriteriaBuilder criteriaBuilder, Root<LedgerEntry> root,
			String colName, LocalDate from, LocalDate to) {
		if (from == null && to == null) {
			return null;
		}
		if (from != null && to != null) {
			if (from.isAfter(to)) {
				throw new InvalidInputsException(
						"Invalid values of from & to " + colName + ". From must be earlier than to");
			} else {
				return criteriaBuilder.between(root.get(colName), from, to);
			}
		} else if (from != null) {
			return criteriaBuilder.greaterThanOrEqualTo(root.get(colName), from);
		} else if (to != null) {
			return criteriaBuilder.lessThanOrEqualTo(root.get(colName), to);
		}
		return null;
	}
}
