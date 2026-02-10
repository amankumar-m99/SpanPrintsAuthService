package com.spanprints.authservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.inventory.CreateInventoryItemRequest;
import com.spanprints.authservice.dto.inventory.InventoryItemResponse;
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
