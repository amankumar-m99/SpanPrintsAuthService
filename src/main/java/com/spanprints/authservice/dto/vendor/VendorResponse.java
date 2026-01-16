package com.spanprints.authservice.dto.vendor;

import java.util.List;

import com.spanprints.authservice.dto.EntityResponseDto;
import com.spanprints.authservice.entity.InventoryHistory;
import com.spanprints.authservice.entity.Vendor;

import io.jsonwebtoken.lang.Collections;
import lombok.Getter;

@Getter
public class VendorResponse extends EntityResponseDto {

	private String email;
	private String name;
	private String address;
	private String primaryPhoneNumber;
	private String alternatePhoneNumber;
	private String createdBy;
	private Long createdById;
	private List<Long> inventoryHistoryIds;

	public VendorResponse(Vendor vendor) {
		super(vendor);
		this.email = vendor.getEmail();
		this.name = vendor.getName();
		this.address = vendor.getAddress();
		this.primaryPhoneNumber = vendor.getPrimaryPhoneNumber();
		this.alternatePhoneNumber = vendor.getAlternatePhoneNumber();
		this.createdBy = vendor.getAccount() != null ? vendor.getAccount().getUsername() : null;
		this.createdById = vendor.getAccount() != null ? vendor.getAccount().getId() : null;
		this.inventoryHistoryIds = vendor.getInventoryHistories() != null
				? vendor.getInventoryHistories().stream().map(InventoryHistory::getId).toList()
				: Collections.emptyList();
	}
}
