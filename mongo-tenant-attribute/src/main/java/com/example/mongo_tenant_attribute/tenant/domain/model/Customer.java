package com.example.mongo_tenant_attribute.tenant.domain.model;

import com.example.mongo_tenant_attribute.tenant_lib.AbstractBaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
@Document
public class Customer extends AbstractBaseEntity {
	@Id
	private String id;
	
	@NotBlank
	private String name;
	
	private String familyName;
	
	private String address;
	
	@Version
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Long version;
}