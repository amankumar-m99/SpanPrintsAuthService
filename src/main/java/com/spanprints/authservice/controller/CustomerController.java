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
import com.spanprints.authservice.dto.customer.CustomerResponse;
import com.spanprints.authservice.dto.customer.UpdateCustomerRequest;
import com.spanprints.authservice.service.CustomerService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping
	public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CreateCustomerRequest request) {
		CustomerResponse customerResponse = new CustomerResponse(customerService.createCustomer(request));
		return new ResponseEntity<>(customerResponse, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
		List<CustomerResponse> list = customerService.getAllCustomers().stream().map(CustomerResponse::new).toList();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<List<CustomerResponse>> searchCustomer(@RequestParam @NotNull @NotBlank String name) {
		List<CustomerResponse> list = customerService.searchCustomersByName(name).stream().map(CustomerResponse::new)
				.toList();
		return ResponseEntity.ok(list);
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable @NotNull @Positive @Min(1) Long id) {
		CustomerResponse customerResponse = new CustomerResponse(customerService.getCustomerById(id));
		return new ResponseEntity<>(customerResponse, HttpStatus.OK);
	}

	@GetMapping("/uuid/{uuid}")
	public ResponseEntity<CustomerResponse> getCustomerByUuid(@PathVariable @NotNull String uuid) {
		CustomerResponse customerResponse = new CustomerResponse(customerService.getCustomerByUuid(uuid));
		return new ResponseEntity<>(customerResponse, HttpStatus.OK);
	}

	@PutMapping("/id/{id}")
	public ResponseEntity<CustomerResponse> updateCustomerById(
			@PathVariable @NotNull(message = "Customer Id is required") @Positive @Min(1) Long id,
			@Valid @RequestBody UpdateCustomerRequest request) {
		CustomerResponse customerResponse = new CustomerResponse(customerService.updateCustomer(id, request));
		return new ResponseEntity<>(customerResponse, HttpStatus.OK);
	}

	@PutMapping("/uuid/{uuid}")
	public ResponseEntity<CustomerResponse> updateCustomerByUuid(
			@PathVariable @NotNull(message = "Customer Id is required") @NotBlank String uuid,
			@Valid @RequestBody UpdateCustomerRequest request) {
		CustomerResponse customerResponse = new CustomerResponse(customerService.updateCustomer(uuid, request));
		return new ResponseEntity<>(customerResponse, HttpStatus.OK);
	}

	@DeleteMapping
	public ResponseEntity<SuccessResponseDto> deleteAllCustomers() {
		SuccessResponseDto responseDto = customerService.deleteAllCustomers();
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@DeleteMapping("/id/{id}")
	public ResponseEntity<SuccessResponseDto> deleteCustomerById(
			@PathVariable("customerId") @NotNull @Positive @Min(1) Long id) {
		SuccessResponseDto responseDto = customerService.deleteCustomerById(id);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@DeleteMapping("/uuid/{uuid}")
	public ResponseEntity<SuccessResponseDto> deleteCustomerByUuid(@PathVariable @NotNull String uuid) {
		SuccessResponseDto responseDto = customerService.deleteCustomerByUuid(uuid);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}
}
