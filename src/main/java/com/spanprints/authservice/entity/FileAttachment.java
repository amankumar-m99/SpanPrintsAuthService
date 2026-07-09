package com.spanprints.authservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
//import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FileAttachment extends AuditableBaseEntity {
	private String originalFileName;
	private String createdFileName;
	private String fileType;
	private Long size;
//	@Lob
//	private byte[] data; // Actual file content

	@ManyToOne
	@JoinColumn(name = "printjob_id", referencedColumnName = "id")
	@JsonIgnore
	private PrintJob printJob;
}
