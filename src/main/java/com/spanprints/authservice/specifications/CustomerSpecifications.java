package com.spanprints.authservice.specifications;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.spanprints.authservice.dto.customer.CustomerFilterRequest;
import com.spanprints.authservice.entity.Customer;
import com.spanprints.authservice.entity.PrintJob;
import com.spanprints.authservice.entity.PrintJobType;
import com.spanprints.authservice.enums.PaymentStatus;
import com.spanprints.authservice.enums.PrintJobStatus;
import com.spanprints.authservice.exception.InvalidInputsException;
import com.spanprints.authservice.util.BasicUtils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class CustomerSpecifications {

	private CustomerSpecifications() {
	}

	public static Specification<Customer> withFilter(CustomerFilterRequest filter) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			// 1. Exact string match (or use criteriaBuilder.like for partial)
			if (filter.getBookNumber() != null && !filter.getBookNumber().isEmpty()) {
				predicates.add(criteriaBuilder.equal(root.get("bookNumber"), filter.getBookNumber()));
			}

			// Inside your Specification loop
			// Many-to-one relationship
			if (filter.getJobTypeIds() != null && !filter.getJobTypeIds().isEmpty()) {
				// 1. Perform a join on the "customer" property declared inside your PrintJob Entity
				Join<PrintJob, PrintJobType> jobTypeJoin = root.join("jobType", JoinType.INNER);

				// 2. Filter on the primary key or column of the joined Customer entity table
				predicates.add(jobTypeJoin.get("id").in(filter.getJobTypeIds()));
			}
			// Many-to-one relationship for customer
			
			List<Predicate> customerFilters = customerFilter(criteriaBuilder, root, filter.getCustomerName(), filter.getCustomerPhone());
			predicates.addAll(customerFilters);

			// 2. Exact match for category
			if (filter.getOrderStatuses() != null && !filter.getOrderStatuses().isEmpty()) {
				Expression<PrintJobStatus> statusExpression = root.get("printJobStatus");
				predicates.add(statusExpression.in(filter.getOrderStatuses()));
			}
			if (filter.getPaymentStatuses() != null && !filter.getPaymentStatuses().isEmpty()) {
				Expression<PaymentStatus> statusExpression = root.get("paymentStatus");
				predicates.add(statusExpression.in(filter.getPaymentStatuses()));
//            	predicates.add(criteriaBuilder.equal(root.get("paymentStatus"), filter.getPaymentStatuses()));
//            	predicates.add(criteriaBuilder.equal(root.get("paymentStatus").as(PaymentStatus.class), filter.getPaymentStatuses()));
			}

			// 3. Range check (Less than or equal to)
			Predicate quantityCriteria = buildCriteriaForCol(criteriaBuilder, root, "quantity", filter.getQuantityMin(), filter.getQuantityMax());
			if(quantityCriteria != null)
				predicates.add(quantityCriteria);

			Predicate totalAmountCriteria = buildCriteriaForCol(criteriaBuilder, root, "totalAmount", filter.getTotalAmountMin(), filter.getTotalAmountMax());
			if(totalAmountCriteria != null)
				predicates.add(totalAmountCriteria);

			Predicate discountedAmountCriteria = buildCriteriaForCol(criteriaBuilder, root, "discountedAmount", filter.getDiscountedAmountMin(), filter.getDiscountedAmountMax());
			if(discountedAmountCriteria != null)
				predicates.add(discountedAmountCriteria);

			Predicate pendingAmountCriteria = buildCriteriaForCol(criteriaBuilder, root, "pendingAmount", filter.getPendingAmountMin(), filter.getPendingAmountMax());
			if(pendingAmountCriteria != null)
				predicates.add(pendingAmountCriteria);

			Predicate dateOfDeliveryCriteria = buildCriteriaForCol(criteriaBuilder, root, "dateOfDelivery", filter.getDeliveryDateMin(), filter.getDeliveryDateMax());
			if(dateOfDeliveryCriteria != null)
				predicates.add(dateOfDeliveryCriteria);

			Predicate dateOfPlacedCriteria = buildCriteriaForCol(criteriaBuilder, root, "dateOfPlaced", filter.getPlacedOnMin(), filter.getPlacedOnMax());
			if(dateOfPlacedCriteria != null)
				predicates.add(dateOfPlacedCriteria);

			// 4. Boolean condition
			/*
			 * if (filter.getIsAvailable() != null) {
			 * predicates.add(criteriaBuilder.equal(root.get("status"),
			 * filter.getIsAvailable())); }
			 */

			// Combine all active conditions with an AND operator
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}

	private static Predicate buildCriteriaForCol(CriteriaBuilder criteriaBuilder, Root<Customer> root, String colName, Long min, Long max) {
		if(min == null && max == null) {
			return null;
		}
		if(min != null && max != null) {
			if(min > max) {
				throw new InvalidInputsException("Invalid values of min & max " + colName + ". Min must be less than max");
			}
			else {
				return criteriaBuilder.between(root.get(colName), min, max);
			}
		}
		else if(min != null) {
			return criteriaBuilder.greaterThanOrEqualTo(root.get(colName), min);
		}
		else if(max != null) {
			return criteriaBuilder.lessThanOrEqualTo(root.get(colName), max);
		}
		return null;
	}

	private static Predicate buildCriteriaForCol(CriteriaBuilder criteriaBuilder, Root<Customer> root, String colName, LocalDate from, LocalDate to) {
		if(from == null && to == null) {
			return null;
		}
		if(from != null && to != null) {
			if(from.isAfter(to)) {
				throw new InvalidInputsException("Invalid values of from & to " + colName + ". From must be earlier than to");
			}
			else {
				return criteriaBuilder.between(root.get(colName), from, to);
			}
		}
		else if(from != null) {
			return criteriaBuilder.greaterThanOrEqualTo(root.get(colName), from);
		}
		else if(to != null) {
			return criteriaBuilder.lessThanOrEqualTo(root.get(colName), to);
		}
		return null;
	}

	private static List<Predicate> customerFilter(CriteriaBuilder criteriaBuilder, Root<Customer> root, String customerName, String customerPhone) {
		if(BasicUtils.isNullOrBlank(customerName) && BasicUtils.isNullOrBlank(customerPhone)) {
			return Collections.emptyList();
		}
		List<Predicate> predicates = new ArrayList<>(2);
		// 1. Perform a join on the "customer" property declared inside your PrintJob Entity
		Join<PrintJob, Customer> customerJoin = root.join("customer", JoinType.INNER);
		
		if (!BasicUtils.isNullOrBlank(customerName)) {
			String searchPattern = "%" + customerName.trim().toLowerCase() + "%";
			// Assumes field name in Customer entity is "name"
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(customerJoin.get("name")),searchPattern));
		}
		if (!BasicUtils.isNullOrBlank(customerPhone)) {
			String searchPattern = "%" + customerPhone.trim().toLowerCase() + "%";
			// Assumes field name in Customer entity is "primaryPhoneNumber"
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(customerJoin.get("primaryPhoneNumber")),searchPattern));
		}
		return predicates;
	}
}
