package com.spanprints.authservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.inventory.AddStockRequest;
import com.spanprints.authservice.dto.inventory.CreateInventoryItemRequest;
import com.spanprints.authservice.dto.inventory.SubtractStockRequest;
import com.spanprints.authservice.dto.inventory.UpdateInventoryItemRequest;
import com.spanprints.authservice.entity.InventoryHistory;
import com.spanprints.authservice.entity.InventoryItem;
import com.spanprints.authservice.entity.LedgerEntry;
import com.spanprints.authservice.entity.LedgerSource;
import com.spanprints.authservice.entity.LedgerType;
import com.spanprints.authservice.entity.Vendor;
import com.spanprints.authservice.enums.InventoryAction;
import com.spanprints.authservice.exception.InvalidInputsException;
import com.spanprints.authservice.exception.inventory.InventoryItemNotFoundException;
import com.spanprints.authservice.repository.InventoryHistoryRepository;
import com.spanprints.authservice.repository.InventoryItemRepository;
import com.spanprints.authservice.repository.LedgerEntryRepository;
import com.spanprints.authservice.repository.VendorRepository;
import com.spanprints.authservice.util.BasicUtils;
import com.spanprints.authservice.util.SecurityUtils;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class InventoryItemService {

	@Autowired
	private LedgerEntryRepository ledgerEntryRepository;

	@Autowired
	private InventoryItemRepository inventoryItemRepository;

	@Autowired
	private InventoryHistoryRepository inventoryHistoryRepository;

	@Autowired
	private VendorRepository vendorRepository;

	@Autowired
	private SecurityUtils securityUtils;

	@Transactional
	public InventoryItem createInventoryItem(CreateInventoryItemRequest request) {
		InventoryItem item = InventoryItem.builder().code(request.getCode()).name(request.getName())
				.description(request.getDescription()).rate(request.getRate()).build();
		item = inventoryItemRepository.save(item);
		return item;
	}

	public InventoryItem updateInventoryItem(UpdateInventoryItemRequest request) {
		InventoryItem inventoryItem = getInventoryItemById(request.getId());
		inventoryItem.setName(request.getName());
		inventoryItem.setCode(request.getCode());
		inventoryItem.setRate(request.getRate());
		inventoryItem.setDescription(request.getDescription());
		return inventoryItemRepository.save(inventoryItem);
	}

	public InventoryItem getInventoryItemById(Long id) {
		return inventoryItemRepository.findById(id).orElseThrow(() -> new InventoryItemNotFoundException("id", id));
	}

	public InventoryItem getInventoryItemByUuid(String uuid) {
		return inventoryItemRepository.findByUuid(uuid)
				.orElseThrow(() -> new InventoryItemNotFoundException("uuid", uuid));
	}

	public List<InventoryItem> getAllInventoryItems() {
		return inventoryItemRepository.findAll();
	}

	@Transactional
	public InventoryItem addStock(@Valid AddStockRequest request) {
		if(request.getQuantity() < 0) {
			throw new InvalidInputsException("Quantity cannot be less than zero.");
		}
		InventoryItem inventoryItem = getInventoryItemById(request.getItemId());
		Long quantity = inventoryItem.getQuantity();
		if(quantity == null) {
			quantity = 0L;
		}
		inventoryItem.setQuantity(quantity + request.getQuantity());
		inventoryItem = inventoryItemRepository.save(inventoryItem);
		Vendor vendor = null;
		if(request.getVendorId() != null) {
			vendor = vendorRepository.findById(request.getVendorId()).orElse(null);
		}
		InventoryHistory history = InventoryHistory.builder().inventoryItem(inventoryItem)
				.amount(request.getAmountPaid()).action(InventoryAction.PURCHASE).quantity(request.getQuantity())
				.vendor(vendor).description(request.getDescription())
				.transactionDateTime(BasicUtils.convertLocalDateToInstant(request.getDateOfTransaction())).build();
		inventoryHistoryRepository.save(history);
		if (request.getAddToLedger() != null && request.getAddToLedger()) {
			LedgerEntry ledgerEntry = LedgerEntry.builder().amount(request.getAmountPaid())
					.ledgerSource(LedgerSource.PURCHASE).ledgerType(LedgerType.DEBIT)
					.description("Purchased Inventory item: " + inventoryItem.getUuid())
					.transactionDateTime(BasicUtils.convertLocalDateToInstant(request.getDateOfTransaction()))
					.account(securityUtils.getRequestingAccount()).build();
			ledgerEntryRepository.save(ledgerEntry);
		}
		return inventoryItem;
	}

	@Transactional
	public InventoryItem subtractStock(@Valid SubtractStockRequest request) {
		if(request.getQuantity() < 0) {
			throw new InvalidInputsException("Quantity cannot be less than zero.");
		}
		InventoryItem inventoryItem = getInventoryItemById(request.getItemId());
		Long quantity = inventoryItem.getQuantity();
		if(quantity < request.getQuantity()) {
			throw new InvalidInputsException("Requested quantity higher than available quantity.");
		}
		inventoryItem.setQuantity(quantity - request.getQuantity());
		inventoryItem = inventoryItemRepository.save(inventoryItem);
		InventoryHistory history = InventoryHistory.builder().inventoryItem(inventoryItem)
				.action(InventoryAction.CONSUMPTION).quantity(request.getQuantity())
				.description(request.getDescription())
				.transactionDateTime(BasicUtils.convertLocalDateToInstant(request.getDateOfTransaction())).build();
		inventoryHistoryRepository.save(history);
		return inventoryItem;
	}

	public SuccessResponseDto deleteAllInventoryItems() {
		inventoryItemRepository.deleteAll();
		return new SuccessResponseDto(HttpStatus.OK, "Deleted all inventory-items");
	}

	public SuccessResponseDto deleteInventoryItemById(Long id) {
		inventoryItemRepository.delete(getInventoryItemById(id));
		return new SuccessResponseDto(HttpStatus.OK, String.format("Deleted inventory-item by id `%d`", id));

	}

	public SuccessResponseDto deleteInventoryItemByUuid(String uuid) {
		inventoryItemRepository.delete(getInventoryItemByUuid(uuid));
		return new SuccessResponseDto(HttpStatus.OK, String.format("Deleted inventory-item by UUID `%s`", uuid));
	}
}
