package com.spanprints.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spanprints.authservice.dto.customer.CustomerResponse;
import com.spanprints.authservice.service.StatisticsService;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {

	@Autowired
	private StatisticsService statisticsService;

	@GetMapping("/top-customers-by-order-count")
	public ResponseEntity<List<CustomerResponse>> getTopCustomersByOrderCount(
			@RequestParam(name = "count", required = false) Integer count) {
		if (count == null) {
			count = 10;
		}
		List<CustomerResponse> list = statisticsService.getTopCustomersByOrderCount(count).stream()
				.map(CustomerResponse::new).toList();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/top-customers-by-amount")
	public ResponseEntity<List<CustomerResponse>> getTopCustomersByAmount(
			@RequestParam(name = "count", required = false) Integer count) {
		if (count == null) {
			count = 10;
		}
		List<CustomerResponse> list = statisticsService.getTopCustomersByOrderCount(count).stream()
				.map(CustomerResponse::new).toList();
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}
