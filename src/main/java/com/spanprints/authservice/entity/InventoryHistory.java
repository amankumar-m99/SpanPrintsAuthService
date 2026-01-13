package com.spanprints.authservice.entity;

import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InventoryHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String uuid;
	private Long count;
	private Double rate;
	private Double amountPaid;
	private Instant updatedAt;
	private Instant createdAt;

	@ManyToOne
	@JoinColumn(name = "inventory_item_id", referencedColumnName = "id")
	@JsonIgnore
	private InventoryItem inventoryItem;

	@JsonProperty("itemId")
	public Long getItemId() {
		return inventoryItem != null ? inventoryItem.getId() : null;
	}

	@ManyToOne
	@JoinColumn(name = "vendor_id", referencedColumnName = "id")
	@JsonIgnore
	private Vendor vendor;

	@JsonProperty("vendorId")
	public Long getVendorId() {
		return vendor != null ? vendor.getId() : null;
	}

}
