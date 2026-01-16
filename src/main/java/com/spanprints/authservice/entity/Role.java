package com.spanprints.authservice.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "roleName", callSuper = false)

public class Role extends AuditableBaseEntity {
	private String roleName;
	private String roleDescription;

}
