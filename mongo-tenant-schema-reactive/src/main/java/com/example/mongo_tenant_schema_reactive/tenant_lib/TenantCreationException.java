package com.example.mongo_tenant_schema_reactive.tenant_lib;

public class TenantCreationException extends RuntimeException {
	public TenantCreationException() {
		super();
	}
	
	public TenantCreationException(String message) {
		super(message);
	}
	
	public TenantCreationException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public TenantCreationException(Throwable cause) {
		super(cause);
	}
	
	protected TenantCreationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
