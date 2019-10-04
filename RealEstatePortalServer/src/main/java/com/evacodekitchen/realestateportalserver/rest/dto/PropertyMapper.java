package com.evacodekitchen.realestateportalserver.rest.dto;

import java.io.IOException;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.web.multipart.MultipartFile;

import com.evacodekitchen.realestateportalserver.usecase.entity.Property;
import com.evacodekitchen.realestateportalserver.usecase.entity.SaleOrRent;


@Mapper
public interface PropertyMapper {
	
	public static PropertyMapper INSTANCE = Mappers.getMapper(PropertyMapper.class);
	
	Property newPropertyDTOToProperty(NewPropertyDTO newPropertyDTO);

	default SaleOrRent stringToSaleOrRent(String saleOrRent) {
		return SaleOrRent.fromString(saleOrRent);
	}
	
	default byte[] mutipartFileToByteArray(MultipartFile value) {
		try {
			return value.getBytes();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	PropertyDTO propertyToPropertyDTO(Property addNewProperty);
	
}