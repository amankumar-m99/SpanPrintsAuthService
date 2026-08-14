package com.spanprints.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.spanprints.authservice.dto.PaginationResponse;
import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.expense.ExpenseFilterAndPaginationRequest;
import com.spanprints.authservice.dto.inventory.AddStockRequest;
import com.spanprints.authservice.dto.inventory.CreateInventoryItemRequest;
import com.spanprints.authservice.dto.inventory.InventoryItemResponse;
import com.spanprints.authservice.dto.inventory.SubtractStockRequest;
import com.spanprints.authservice.dto.inventory.UpdateInventoryItemRequest;
import com.spanprints.authservice.entity.InventoryItem;
import com.spanprints.authservice.service.InventoryItemService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/inventory-items")
public class InventoryItemController {

	@Autowired
	private InventoryItemService inventoryItemService;

	@PostMapping
	@Transactional
	public ResponseEntity<InventoryItemResponse> createInventoryItem(
			@Valid @RequestBody CreateInventoryItemRequest request) {
		InventoryItemResponse inventoryItem = new InventoryItemResponse(
				inventoryItemService.createInventoryItem(request));
		return new ResponseEntity<>(inventoryItem, HttpStatus.CREATED);
	}

	@GetMapping
	public List<InventoryItemResponse> getAllInventoryItems() {
		return inventoryItemService.getAllInventoryItems().stream().map(InventoryItemResponse::new).toList();
	}

	@PostMapping("/paginated")
	public PaginationResponse<InventoryItem, InventoryItemResponse> getPaginatedCustomers(
			@Valid @RequestBody ExpenseFilterAndPaginationRequest filter) {
		Page<InventoryItem> filteredProductsPaginated = inventoryItemService.getFilteredPaginatedExpenses(filter);
		return new PaginationResponse<>(filteredProductsPaginated, InventoryItemResponse::new);
	}

	@PutMapping
	public ResponseEntity<InventoryItemResponse> updateInventoryItem(
			@Valid @RequestBody UpdateInventoryItemRequest request) {
		InventoryItemResponse inventoryItem = new InventoryItemResponse(
				inventoryItemService.updateInventoryItem(request));
		return new ResponseEntity<>(inventoryItem, HttpStatus.CREATED);
	}

	@PutMapping("add-stock")
	public ResponseEntity<InventoryItemResponse> addStock(@Valid @RequestBody AddStockRequest request) {
		InventoryItemResponse inventoryItem = new InventoryItemResponse(inventoryItemService.addStock(request));
		return new ResponseEntity<>(inventoryItem, HttpStatus.CREATED);
	}

	@PutMapping("subtract-stock")
	public ResponseEntity<InventoryItemResponse> subtractStock(@Valid @RequestBody SubtractStockRequest request) {
		InventoryItemResponse inventoryItem = new InventoryItemResponse(inventoryItemService.subtractStock(request));
		return new ResponseEntity<>(inventoryItem, HttpStatus.CREATED);
	}

	@DeleteMapping
	public ResponseEntity<SuccessResponseDto> deleteAllInventoryItems() {
		SuccessResponseDto responseDto = inventoryItemService.deleteAllInventoryItems();
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@DeleteMapping("/id/{id}")
	public ResponseEntity<SuccessResponseDto> deleteInventoryItemById(
			@PathVariable @NotNull @Positive @Min(1) Long id) {
		SuccessResponseDto responseDto = inventoryItemService.deleteInventoryItemById(id);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}

	@DeleteMapping("/uuid/{uuid}")
	public ResponseEntity<SuccessResponseDto> deleteInventoryItemByUuid(@PathVariable @NotNull String uuid) {
		SuccessResponseDto responseDto = inventoryItemService.deleteInventoryItemByUuid(uuid);
		return new ResponseEntity<>(responseDto, responseDto.getStatus());
	}
}
