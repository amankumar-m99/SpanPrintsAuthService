package com.spanprints.authservice.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.spanprints.authservice.dto.printjob.PrintJobFilterRequest;
import com.spanprints.authservice.entity.Customer;
import com.spanprints.authservice.entity.PrintJob;
import com.spanprints.authservice.entity.PrintJobType;
import com.spanprints.authservice.enums.PaymentStatus;
import com.spanprints.authservice.enums.PrintJobStatus;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public class PrintJobSpecifications {

	private PrintJobSpecifications() {
	}

	public static Specification<PrintJob> withFilter(PrintJobFilterRequest filter) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			// 1. Exact string match (or use criteriaBuilder.like for partial)
			if (filter.getBookNumber() != null && !filter.getBookNumber().isEmpty()) {
				predicates.add(criteriaBuilder.equal(root.get("bookNumber"), filter.getBookNumber()));
			}

			// Inside your Specification loop
			if (filter.getJobTypeIds() != null && !filter.getJobTypeIds().isEmpty()) {
				// 1. Perform a join on the "customer" property declared inside your PrintJob
				// Entity
				Join<PrintJob, PrintJobType> jobTypeJoin = root.join("jobType", JoinType.INNER);

				// 2. Filter on the primary key or column of the joined Customer entity table
				predicates.add(jobTypeJoin.get("id").in(filter.getJobTypeIds()));
			}

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
			/*
			 * if (filter.getMaxPrice() != null) {
			 * predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"),
			 * filter.getMaxPrice())); }
			 */

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
}
