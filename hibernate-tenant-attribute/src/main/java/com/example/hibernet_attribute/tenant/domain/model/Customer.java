package com.example.hibernet_attribute.tenant.domain.model;

import com.example.hibernet_attribute.tenant_lib.AbstractBaseEntity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Version;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
@Entity
public class Customer extends AbstractBaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotBlank
	private String name;
	
	private String familyName;
	
	private String address;
	
	@Version
	@Getter(AccessLevel.NONE)
	@Setter(AccessLevel.NONE)
	private Long version;
}