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
import org.springframework.web.bind.annotation.RestController;

import com.spanprints.authservice.dto.CustomerRequestDto;
import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.UpdateCustomerRequestDto;
import com.spanprints.authservice.entity.Customer;
import com.spanprints.authservice.service.CustomerService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("")
public class CustomerController {

	@Autowired
	private CustomerService customerService;

	@PostMapping("/customer")
	public ResponseEntity<Customer> addCustomer(@Valid @RequestBody CustomerRequestDto dto) {
		return new ResponseEntity<>(customerService.createCustomer(dto), HttpStatus.CREATED);
	}

	@GetMapping("/customer/id/{customerId}")
	public ResponseEntity<Customer> getCustomerById(@PathVariable("customerId") @NotNull @Positive @Min(1) Long id) {
		return new ResponseEntity<>(customerService.getCustomerById(id), HttpStatus.OK);
	}

	@GetMapping("/customer/uuid/{customerUuid}")
	public ResponseEntity<Customer> getCustomerByUuid(@PathVariable("customerUuid") @NotNull String uuid) {
		return new ResponseEntity<>(customerService.getCustomerByUuid(uuid), HttpStatus.OK);
	}

	@GetMapping("/customers")
	public ResponseEntity<List<Customer>> getAllCustomers() {
		return new ResponseEntity<>(customerService.getAllCustomers(), HttpStatus.OK);
	}

	@PutMapping("/customer")
	public ResponseEntity<Customer> updateCustomer(@Valid @RequestBody UpdateCustomerRequestDto dto) {
		return new ResponseEntity<>(customerService.updateCustomer(dto), HttpStatus.OK);
	}

	@DeleteMapping("/customer/{customerId}")
	public ResponseEntity<SuccessResponseDto> deleteCustomer(
			@PathVariable("customerId") @NotNull @Positive @Min(1) Long id) {
		SuccessResponseDto responseDto = customerService.deleteCustomerById(id);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@DeleteMapping("/customers")
	public ResponseEntity<SuccessResponseDto> deleteAllCustomers() {
		SuccessResponseDto responseDto = customerService.deleteAllCustomers();
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}
}
