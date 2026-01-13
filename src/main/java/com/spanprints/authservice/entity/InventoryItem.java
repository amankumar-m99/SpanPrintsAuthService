package com.spanprints.authservice.entity;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.jsonwebtoken.lang.Collections;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
public class InventoryItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String uuid;
	private String name;
	private String description;
	private Double rate;
	private Instant updatedAt;
	private Instant createdAt;

	@OneToOne(mappedBy = "inventoryItem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private Inventory inventory;

	@JsonProperty("inventoryId")
	public Long getInventoryId() {
		return inventory != null ? inventory.getId() : null;
	}

	@OneToMany(mappedBy = "inventoryItem", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	@JsonIgnore
	private List<InventoryHistory> inventoryHistories;

	@JsonProperty("inventoryHistoryIds")
	public List<Long> getInventoryHistoryIds() {
		if (inventoryHistories == null) {
			return Collections.emptyList();
		}
		return inventoryHistories.stream().map(o -> o.getId()).toList();
	}
}
