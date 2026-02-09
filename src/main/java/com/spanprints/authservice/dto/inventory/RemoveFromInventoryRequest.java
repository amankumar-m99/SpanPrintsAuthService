package com.spanprints.authservice.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RemoveFromInventoryRequest {

	private Long itemId;
	private Long quantity;

}
