package com.example.mongo_tenant_attribute.tenant.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class CustomerDto {
	private String id;
	
	@NotBlank
	private String name;
	
	private String familyName;
	
	private String address;
	
}