package com.spanprints.authservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.entity.Customer;
import com.spanprints.authservice.repository.CustomerRepository;
import com.spanprints.authservice.util.SecurityUtils;

@Service
public class StatisticsService {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private SecurityUtils securityUtils;

	public List<Customer> getTopCustomersByOrderCount(int count) {
		return null;
	}

	public List<Customer> getTopCustomersByAmount(int count) {
		return null;
	}
}
