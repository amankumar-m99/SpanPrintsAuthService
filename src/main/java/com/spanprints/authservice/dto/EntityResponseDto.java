package com.spanprints.authservice.dto;

import java.time.Instant;

import com.spanprints.authservice.entity.AuditableBaseEntity;

import lombok.Getter;


@Getter
public abstract class EntityResponseDto {

	protected Long id;
	protected String uuid;
	protected Instant createdAt;
	protected Instant updatedAt;

	protected EntityResponseDto(AuditableBaseEntity entity) {
		this.id = entity.getId();
		this.uuid = entity.getUuid();
		this.createdAt = entity.getCreatedAt();
		this.updatedAt = entity.getUpdatedAt();
	}
}
