package com.example.hibernet_attribute.tenant_lib;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serial;
import java.io.Serializable;

@SuperBuilder
@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FilterDef(
		name = AbstractBaseEntity.FILTER_NAME,
		parameters = @ParamDef(name = AbstractBaseEntity.FILTER_ARGUMENT_NAME, type = "string"))
@Filter(name = AbstractBaseEntity.FILTER_NAME,
		condition = AbstractBaseEntity.ID_PROPERTY_NAME + " = :" + AbstractBaseEntity.FILTER_ARGUMENT_NAME)
@EntityListeners(TenantListener.class)
public abstract class AbstractBaseEntity implements Serializable {
    @Serial
	private static final long serialVersionUID = 1L;
	
	public static final String FILTER_NAME = "tenantFilter";
	public static final String ID_PROPERTY_NAME = "tenant_Id";
	public static final String FILTER_ARGUMENT_NAME = "tenantId";
	
//	@TenantId
	@Column(name = ID_PROPERTY_NAME)
    private String tenantId;

}