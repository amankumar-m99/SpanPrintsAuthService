package com.spanprints.authservice.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.customer.CreateCustomerRequest;
import com.spanprints.authservice.dto.customer.UpdateCustomerRequest;
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

	public Customer createCustomer(CreateCustomerRequest request) {
		throwIfNameAlreadyExists(request.getName());
		Account account = securityUtils.getRequestingAccount();
		Customer customer = Customer.builder().id(null).uuid(UUID.randomUUID().toString()).name(request.getName())
				.email(request.getEmail()).primaryPhoneNumber(request.getPrimaryPhoneNumber())
				.alternatePhoneNumber(request.getAlternatePhoneNumber()).address(request.getAddress()).account(account)
				.createdAt(Instant.now()).updatedAt(Instant.now()).build();
		return customerRepository.save(customer);
	}

	public Customer updateCustomer(UpdateCustomerRequest request) {
		Customer customer = getCustomerById(request.getId());
		customer.setEmail(request.getEmail());
		customer.setName(request.getName());
		customer.setPrimaryPhoneNumber(request.getPrimaryPhoneNumber());
		customer.setAlternatePhoneNumber(request.getAlternatePhoneNumber());
		customer.setAddress(request.getAddress());
		customer.setUpdatedAt(Instant.now());
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
		return customerRepository.findByName(name).orElseThrow(() -> new CustomerNotFoundException("name", name));
	}

	public List<Customer> searchCustomersByName(String name) {
        return customerRepository.findByNameContainingIgnoreCase(name);
    }

	public List<Customer> getCustomerByPrimaryPhoneNumber(String primaryPhoneNumber) {
		return customerRepository.findAllByPrimaryPhoneNumber(primaryPhoneNumber)
				.orElseThrow(() -> new CustomerNotFoundException("primary phone number", primaryPhoneNumber));
	}

	private void throwIfNameAlreadyExists(String name) {
		if (!customerRepository.findByName(name).isEmpty()) {
			throw new CustomerAlreadyExistsException("A customer already exists with the name " + name);
		}
	}

	public SuccessResponseDto deleteAllCustomers() {
		customerRepository.deleteAll();
		return new SuccessResponseDto(HttpStatus.OK, "Deleted all customers");

	}

	public SuccessResponseDto deleteCustomerById(Long id) {
		customerRepository.delete(getCustomerById(id));
		return new SuccessResponseDto(HttpStatus.OK, String.format("Deleted customer by id `%d`", id));

	}

	public SuccessResponseDto deleteCustomerByUuid(String uuid) {
		customerRepository.delete(getCustomerByUuid(uuid));
		return new SuccessResponseDto(HttpStatus.OK, String.format("Deleted customer by UUID `%s`", uuid));
	}

	public SuccessResponseDto deleteCustomerByName(String name) {
		customerRepository.delete(getCustomerByName(name));
		return new SuccessResponseDto(HttpStatus.OK, String.format("Deleted customer by name `%s`", name));
	}
}
