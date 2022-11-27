package com.example.mongo_tenant_schema_reactive.app.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
@Document
public class Customer {
	@MongoId
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