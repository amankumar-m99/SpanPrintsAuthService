package com.spanprints.authservice.dto.inventory;

import com.spanprints.authservice.dto.PaginationRequest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InventoryFilterAndPaginationRequest {

	private PaginationRequest paginationRequest;
}
