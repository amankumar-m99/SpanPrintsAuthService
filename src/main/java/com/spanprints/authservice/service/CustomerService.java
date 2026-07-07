package com.spanprints.authservice.service;

import java.util.List;
import java.util.Optional;

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
		throwIfPhoneNumberAlreadyExists(request.getPrimaryPhoneNumber());
		Account account = securityUtils.getRequestingAccount();
		Customer customer = Customer.builder().name(request.getName()).email(request.getEmail())
				.primaryPhoneNumber(request.getPrimaryPhoneNumber())
				.alternatePhoneNumber(request.getAlternatePhoneNumber()).address(request.getAddress()).account(account)
				.build();
		return customerRepository.save(customer);
	}

	public Customer createCustomerByPhoneNumber(String phoneNumber){
		throwIfPhoneNumberAlreadyExists(phoneNumber);
		Account account = securityUtils.getRequestingAccount();
		Customer customer = Customer.builder().name("").email("")
				.primaryPhoneNumber(phoneNumber)
				.alternatePhoneNumber("").address("").account(account)
				.build();
		return customerRepository.save(customer);
	}

	public Customer updateCustomer(Long id, UpdateCustomerRequest request) {
		Customer customer = getCustomerById(id);
		return updateCustomer(customer, request);
	}

	public Customer updateCustomer(String uuid, UpdateCustomerRequest request) {
		Customer customer = getCustomerByUuid(uuid);
		return updateCustomer(customer, request);
	}

	private Customer updateCustomer(Customer customer, UpdateCustomerRequest request) {
		customer.setEmail(request.getEmail());
		customer.setName(request.getName());
		customer.setPrimaryPhoneNumber(request.getPrimaryPhoneNumber());
		customer.setAlternatePhoneNumber(request.getAlternatePhoneNumber());
		customer.setAddress(request.getAddress());
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

	public boolean doesCustomerExistsByPhoneNumber(String primaryPhoneNumber) {
		Optional<List<Customer>> optional = customerRepository.findAllByPrimaryPhoneNumber(primaryPhoneNumber);
		return optional.isPresent() && !optional.get().isEmpty();
	}

	public List<Customer> getCustomersByPrimaryPhoneNumber(String primaryPhoneNumber) {
		return customerRepository.findAllByPrimaryPhoneNumber(primaryPhoneNumber)
				.orElseThrow(() -> new CustomerNotFoundException("primary phone number", primaryPhoneNumber));
	}

	public Customer getCustomerByPrimaryPhoneNumber(String primaryPhoneNumber) {
		return getCustomersByPrimaryPhoneNumber(primaryPhoneNumber).get(0);
	}

	private void throwIfPhoneNumberAlreadyExists(String phoneNumber) {
		if(doesCustomerExistsByPhoneNumber(phoneNumber)){
			throw new CustomerAlreadyExistsException("A customer already exists with phone number " + phoneNumber);
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
