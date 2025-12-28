package com.spanprints.authservice.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.AddCustomerRequestDto;
import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.UpdateCustomerRequestDto;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.Customer;
import com.spanprints.authservice.exception.customer.CustomerAlreadyExistsException;
import com.spanprints.authservice.exception.customer.CustomerNotFoundException;
import com.spanprints.authservice.repository.CustomerRepository;
import com.spanprints.authservice.util.SecurityUtils;

@Service
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private SecurityUtils securityUtils;

	public Customer createCustomer(AddCustomerRequestDto dto) {
		throwIfNameAlreadyExists(dto.getUsername());
		Account account = securityUtils.getRequestingAccount();
		Customer customer = Customer.builder().id(null).uuid(UUID.randomUUID().toString()).username(dto.getUsername())
				.email(dto.getEmail()).primaryPhoneNumber(dto.getPrimaryPhoneNumber())
				.alternatePhoneNumber(dto.getAlternatePhoneNumber()).addedBy(account).dateAdded(LocalDateTime.now())
				.build();
		return customerRepository.save(customer);
	}

	public List<Customer> getAllCustomers() {
		return customerRepository.findAll();
	}

	public Customer getCustomerById(Long id) {
		return customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException("id", id));
	}

	public Customer getCustomerByUuid(String uuid) {
		return customerRepository.findByUuid(uuid).orElseThrow(() -> new CustomerNotFoundException("UUID", uuid));
	}

	public List<Customer> getAllCustomersByEmail(String email) {
		return customerRepository.findAllByEmail(email)
				.orElseThrow(() -> new CustomerNotFoundException("email", email));
	}

	public Customer getCustomerByName(String name) {
		return customerRepository.findByUsername(name).orElseThrow(() -> new CustomerNotFoundException("name", name));
	}

	public List<Customer> getCustomerByPrimaryPhoneNumber(String primaryPhoneNumber) {
		return customerRepository.findAllByPrimaryPhoneNumber(primaryPhoneNumber)
				.orElseThrow(() -> new CustomerNotFoundException("primary phone number", primaryPhoneNumber));
	}

	private void throwIfNameAlreadyExists(String name) {
		if (!customerRepository.findByUsername(name).isEmpty()) {
			throw new CustomerAlreadyExistsException("A customer already exists with the name " + name);
		}
	}

	public Customer updateCustomer(UpdateCustomerRequestDto dto) {
		Customer customer = getCustomerById(dto.getId());
		customer.setEmail(dto.getEmail());
		customer.setUsername(dto.getName());
		customer.setPrimaryPhoneNumber(dto.getPrimaryPhoneNumber());
		customer.setAlternatePhoneNumber(dto.getAlternatePhoneNumber());
		return customerRepository.save(customer);
	}

	public SuccessResponseDto deleteAllCustomers() {
		customerRepository.deleteAll();
		return new SuccessResponseDto(HttpStatus.OK, String.format("Deleted all customers"));

	}

	public SuccessResponseDto deleteCustomerById(Long id) {
		customerRepository.delete(getCustomerById(id));
		return new SuccessResponseDto(HttpStatus.OK, String.format("Deleted customer by id `%d`", id));

	}

	public SuccessResponseDto deleteCustomerByUuid(String uuid) {
		customerRepository.delete(getCustomerByUuid(uuid));
		return new SuccessResponseDto(HttpStatus.OK, String.format("Deleted customer by UUID `%d`", uuid));
	}

	public SuccessResponseDto deleteCustomerByName(String name) {
		customerRepository.delete(getCustomerByName(name));
		return new SuccessResponseDto(HttpStatus.OK, String.format("Deleted customer by name `%d`", name));
	}
}
