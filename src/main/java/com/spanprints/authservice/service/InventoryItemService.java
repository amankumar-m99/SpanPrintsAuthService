package com.spanprints.authservice.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spanprints.authservice.dto.inventory.CreateInventoryItemRequest;
import com.spanprints.authservice.entity.Account;
import com.spanprints.authservice.entity.InventoryHistory;
import com.spanprints.authservice.entity.InventoryItem;
import com.spanprints.authservice.entity.LedgerEntry;
import com.spanprints.authservice.entity.LedgerSource;
import com.spanprints.authservice.entity.LedgerType;
import com.spanprints.authservice.enums.InventoryAction;
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
		InventoryItem item = InventoryItem.builder().name(request.getName()).description(request.getDescription())
				.rate(request.getRate()).availableCount(request.getCount()).build();
		BigDecimal amount = item.getRate().multiply(new BigDecimal(request.getCount()));
		if (request.getAddToLedger()) {
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

	public List<InventoryItem> getAllInventoryItems() {
		return inventoryItemRepository.findAll();
	}

}
