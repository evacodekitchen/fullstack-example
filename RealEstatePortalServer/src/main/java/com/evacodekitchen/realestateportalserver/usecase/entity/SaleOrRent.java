package com.evacodekitchen.realestateportalserver.usecase.entity;

import java.util.stream.Stream;

public enum SaleOrRent {
	SALE("SALE"), RENT("RENT");

	private SaleOrRent(String code) {
		this.code = code;
	}

	private String code;

	public String toString() {
		return name();
	}

	public String getCode() {
		return code;
	}

	public static SaleOrRent fromString(String aCode) {
		return Stream.of(values()).filter(saleOrRent -> saleOrRent.getCode().equals(aCode)).findFirst().get();		
	}
}
