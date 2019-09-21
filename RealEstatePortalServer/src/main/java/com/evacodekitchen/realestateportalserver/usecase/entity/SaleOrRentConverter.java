package com.evacodekitchen.realestateportalserver.usecase.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SaleOrRentConverter implements AttributeConverter<SaleOrRent, String> {

	@Override
	public String convertToDatabaseColumn(SaleOrRent attribute) {
		if (attribute == null) {
			return null;
		}
		return attribute.getCode();
	}

	@Override
	public SaleOrRent convertToEntityAttribute(String dbData) {
		if (dbData == null)
			return null;
		return SaleOrRent.fromString(dbData);
	}

}
