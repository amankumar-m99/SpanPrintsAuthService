package com.spanprints.authservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
//import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FileAttachment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String fileName;
	private String fileType;

//	@Lob
//	private byte[] data; // Actual file content

	@ManyToOne
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	@JsonIgnore
	private Order order;

	@JsonProperty("orderId") // will be included in JSON
	public Long getOrderId() {
		return order != null ? order.getId() : null;
	}
}
