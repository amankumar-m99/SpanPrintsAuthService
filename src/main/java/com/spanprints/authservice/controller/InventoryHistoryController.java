package com.spanprints.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spanprints.authservice.dto.inventory.InventoryHistoryResponse;
import com.spanprints.authservice.repository.InventoryHistoryRepository;

@RestController
@RequestMapping("/inventory-history")
public class InventoryHistoryController {

	@Autowired
	private InventoryHistoryRepository inventoryHistoryRepository;

	@GetMapping
	public List<InventoryHistoryResponse> getAll() {
		try {
			List<InventoryHistoryResponse> list = inventoryHistoryRepository.findAll().stream()
					.map(InventoryHistoryResponse::new).toList();
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
