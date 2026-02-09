package com.spanprints.authservice.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.SuccessResponseDto;
import com.spanprints.authservice.dto.inventory.CreateInventoryItemRequest;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.InventoryHistory;
import com.spanprints.authservice.entity.InventoryItem;
import com.spanprints.authservice.entity.LedgerEntry;
import com.spanprints.authservice.entity.LedgerSource;
import com.spanprints.authservice.entity.LedgerType;
import com.spanprints.authservice.enums.InventoryAction;
import com.spanprints.authservice.exception.inventory.InventoryItemNotFoundException;
import com.spanprints.authservice.repository.InventoryHistoryRepository;
import com.spanprints.authservice.repository.InventoryItemRepository;
import com.spanprints.authservice.repository.LedgerEntryRepository;
import com.spanprints.authservice.util.SecurityUtils;

import jakarta.transaction.Transactional;

@Service
public class InventoryItemService {

	@Autowired
	private LedgerEntryRepository ledgerEntryRepository;

	@Autowired
	private InventoryItemRepository inventoryItemRepository;

	@Autowired
	private InventoryHistoryRepository inventoryHistoryRepository;

	@Autowired
	private SecurityUtils securityUtils;

	@Transactional
	public InventoryItem createInventoryItem(CreateInventoryItemRequest request) {
		Account account = securityUtils.getRequestingAccount();
		InventoryItem item = InventoryItem.builder().code(request.getCode()).name(request.getName())
				.description(request.getDescription()).rate(request.getRate()).quantity(request.getQuantity()).build();
		BigDecimal amount = item.getRate().multiply(new BigDecimal(request.getQuantity()));
		if (request.getAddToLedger().equals(true)) {
			LedgerEntry ledgerEntry = LedgerEntry.builder().account(account).amount(amount).ledgerType(LedgerType.DEBIT)
					.ledgerSource(LedgerSource.PURCHASE).transactionDateTime(Instant.now()).build();
			ledgerEntryRepository.save(ledgerEntry);
		}
		item = inventoryItemRepository.save(item);
		InventoryHistory history = InventoryHistory.builder().inventoryItem(item).rate(item.getRate()).amount(amount)
				.action(InventoryAction.PURCHASE).build();
		inventoryHistoryRepository.save(history);
		return item;
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
