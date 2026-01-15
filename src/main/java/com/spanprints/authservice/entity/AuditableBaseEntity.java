package com.spanprints.authservice.entity;

import java.time.Instant;
import java.util.UUID;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableBaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Column(nullable = false, unique = true, updatable = false)
	protected String uuid;

	@CreatedDate
	@Column(updatable = false, nullable = false)
	protected Instant createdAt;

	@LastModifiedDate
	protected Instant updatedAt;

	@PrePersist
	public void prePersist() {
		if (this.uuid == null) {
			this.uuid = UUID.randomUUID().toString();
		}
	}

	public Long getId() {
		return id;
	}

	public String getUuid() {
		return uuid;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}
}
