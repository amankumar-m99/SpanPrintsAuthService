package com.spanprints.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.customer.CreateCustomerRequest;
import com.spanprints.authservice.dto.customer.UpdateCustomerRequest;
import com.spanprints.authservice.entity.Customer;
import com.spanprints.authservice.service.CustomerService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/customer")
	public ResponseEntity<Customer> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
		return new ResponseEntity<>(customerService.createCustomer(request), HttpStatus.CREATED);
	}

	@PutMapping("/customer")
	public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody UpdateCustomerRequest request) {
		return new ResponseEntity<>(customerService.updateCustomer(request), HttpStatus.OK);
	}

	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
	}

	@GetMapping("/customer/search")
	public ResponseEntity<List<Customer>> searchCustomer(@RequestParam @NotNull @NotBlank String name) {
		List<Customer> customers = customerService.searchCustomersByName(name);
		return ResponseEntity.ok(customers);
	}

	@GetMapping("/customer/id/{customerId}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") @NotNull @Positive @Min(1) Long id) {
		return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
	}

	@GetMapping("/customer/uuid/{customerUuid}")
	public ResponseEntity<Customer> getCustomerByUuid(@PathVariable("customerUuid") @NotNull String uuid) {
		return new ResponseEntity<>(customerService.getCustomerByUuid(uuid), HttpStatus.OK);
	}

	@DeleteMapping("/customer/id/{customerId}")
	public ResponseEntity<SuccessResponseDto> deleteCustomerById(
			@PathVariable("customerId") @NotNull @Positive @Min(1) Long id) {
		SuccessResponseDto responseDto = customerService.deleteCustomerById(id);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@DeleteMapping("/customer/uuid/{customerId}")
	public ResponseEntity<SuccessResponseDto> deleteCustomerByUuid(@PathVariable("customerId") @NotNull String uuid) {
		SuccessResponseDto responseDto = customerService.deleteCustomerByUuid(uuid);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@DeleteMapping("/customers")
	public ResponseEntity<SuccessResponseDto> deleteAllCustomers() {
		SuccessResponseDto responseDto = customerService.deleteAllCustomers();
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}
}
