package com.example.mongo_tenant_attribute.tenant_lib;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractBaseEntity implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;
	
	public static final String FILTER_NAME = "tenantFilter";
	public static final String ID_PROPERTY_NAME = "tenant_Id";
	public static final String FILTER_ARGUMENT_NAME = "tenantId";
	
	//	@TenantId
	private String tenantId;
	
}