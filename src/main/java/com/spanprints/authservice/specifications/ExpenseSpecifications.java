package com.spanprints.authservice.specifications;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.spanprints.authservice.dto.expense.ExpenseFilterAndPaginationRequest;
import com.spanprints.authservice.entity.Expense;
import com.spanprints.authservice.enums.ExpenseType;
import com.spanprints.authservice.exception.InvalidInputsException;
import com.spanprints.authservice.util.BasicUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class ExpenseSpecifications {

	private ExpenseSpecifications() {
	}

	public static Specification<Expense> withFilter(ExpenseFilterAndPaginationRequest filter) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (!BasicUtils.isNullOrBlank(filter.getDescription())) {
				String searchPattern = "%" + filter.getDescription().trim().toLowerCase() + "%";
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), searchPattern));
			}

			if (filter.getExpenseTypes() != null && !filter.getExpenseTypes().isEmpty()) {
				Expression<ExpenseType> expenseTypeExpression = root.get("expenseType");
				predicates.add(expenseTypeExpression.in(filter.getExpenseTypes()));
			}

			Predicate dateOfExpenseCriteria = buildCriteriaForCol(criteriaBuilder, root, "dateOfExpense",
					filter.getDateOfExpenseFrom(), filter.getDateOfExpenseTo());
			if (dateOfExpenseCriteria != null)
				predicates.add(dateOfExpenseCriteria);

			Predicate amountCriteria = buildCriteriaForCol(criteriaBuilder, root, "amount", filter.getAmountMin(),
					filter.getAmountMax());
			if (amountCriteria != null)
				predicates.add(amountCriteria);

			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}

	private static Predicate buildCriteriaForCol(CriteriaBuilder criteriaBuilder, Root<Expense> root, String colName,
			BigDecimal min, BigDecimal max) {
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

	private static Predicate buildCriteriaForCol(CriteriaBuilder criteriaBuilder, Root<Expense> root, String colName,
			LocalDate from, LocalDate to) {
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
