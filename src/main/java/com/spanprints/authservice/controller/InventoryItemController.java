package com.spanprints.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spanprints.authservice.dto.inventory.CreateInventoryItemRequest;
import com.spanprints.authservice.dto.inventory.InventoryItemResponse;
import com.spanprints.authservice.service.InventoryItemService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/inventory-items")
public class InventoryItemController {

	@Autowired
	private InventoryItemService inventoryItemService;

	@PostMapping
	@Transactional
	public ResponseEntity<InventoryItemResponse> createExpense(@Valid @RequestBody CreateInventoryItemRequest request) {
		InventoryItemResponse inventoryItem = new InventoryItemResponse(
				inventoryItemService.createInventoryItem(request));
		return new ResponseEntity<>(inventoryItem, HttpStatus.CREATED);
	}

	@GetMapping
	public List<InventoryItemResponse> getAllExpenses() {
		List<InventoryItemResponse> list = inventoryItemService.getAllInventoryItems().stream()
				.map(InventoryItemResponse::new).toList();
		return list;
	}
}
