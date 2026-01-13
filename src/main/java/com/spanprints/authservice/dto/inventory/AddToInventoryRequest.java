package com.spanprints.authservice.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddToInventoryRequest {

	private Long vendorId;
	private Long itemId;
	private Long count;
	private Double rate;
	private Double amountPaid;

}
