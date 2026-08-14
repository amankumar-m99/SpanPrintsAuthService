package com.spanprints.authservice.specifications;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.spanprints.authservice.dto.customer.CustomerFilterAndPaginationRequest;
import com.spanprints.authservice.entity.Customer;
import com.spanprints.authservice.util.BasicUtils;

import jakarta.persistence.criteria.Predicate;

public class CustomerSpecifications {

	private CustomerSpecifications() {
	}

	public static Specification<Customer> withFilter(CustomerFilterAndPaginationRequest filter) {
		return (root, query, criteriaBuilder) -> {
			List<Predicate> predicates = new ArrayList<>();

			if (!BasicUtils.isNullOrBlank(filter.getName())) {
				String searchPattern = "%" + filter.getName().trim().toLowerCase() + "%";
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), searchPattern));
			}

			if (!BasicUtils.isNullOrBlank(filter.getEmail())) {
				String searchPattern = "%" + filter.getEmail().trim().toLowerCase() + "%";
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), searchPattern));
			}

			if (!BasicUtils.isNullOrBlank(filter.getAddress())) {
				String searchPattern = "%" + filter.getAddress().trim().toLowerCase() + "%";
				predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), searchPattern));
			}

			if (!BasicUtils.isNullOrBlank(filter.getPhone())) {
				String searchPattern = "%" + filter.getPhone().trim().toLowerCase() + "%";
				predicates.add(
						criteriaBuilder.like(criteriaBuilder.lower(root.get("primaryPhoneNumber")), searchPattern));
				predicates.add(
						criteriaBuilder.like(criteriaBuilder.lower(root.get("alternatePhoneNumber")), searchPattern));
			}
			return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
		};
	}
}
