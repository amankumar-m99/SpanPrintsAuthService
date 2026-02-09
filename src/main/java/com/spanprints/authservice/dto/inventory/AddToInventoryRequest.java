package com.spanprints.authservice.dto.inventory;

import java.math.BigDecimal;

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
	private Long quantity;
	private Double rate;
	private BigDecimal amountPaid;

}
