package com.spanprints.authservice.entity;

import java.math.BigDecimal;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spanprints.authservice.enums.InventoryAction;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

	@ManyToOne
	@JoinColumn(name = "inventory_item_id", referencedColumnName = "id")
	@JsonIgnore
	private InventoryItem inventoryItem;

	private BigDecimal rate;
	private BigDecimal amountPaid;

	@Enumerated(EnumType.STRING)
	private InventoryAction action;

	private Long quantity; // delta (+ / -)

	@ManyToOne
	@JoinColumn(name = "vendor_id", referencedColumnName = "id")
	@JsonIgnore
	private Vendor vendor;

	@OneToOne
	@JoinColumn(name = "printJob_id", referencedColumnName = "id")
	@JsonIgnore
	private PrintJob printJob;

	private Instant updatedAt;
	private Instant createdAt;

	@JsonProperty("itemId")
	public Long getItemId() {
		return inventoryItem != null ? inventoryItem.getId() : null;
	}

	@JsonProperty("vendorId")
	public Long getVendorId() {
		return vendor != null ? vendor.getId() : null;
	}
}
