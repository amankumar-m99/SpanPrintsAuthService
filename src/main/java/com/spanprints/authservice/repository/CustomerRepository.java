package com.spanprints.authservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spanprints.authservice.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	public Optional<List<Customer>> findAllByEmail(String email);

	public Optional<Customer> findByName(String name);

	public Optional<Customer> findByUuid(String uuid);

	public Optional<List<Customer>> findAllByPrimaryPhoneNumber(String primaryPhoneNumber);
}
