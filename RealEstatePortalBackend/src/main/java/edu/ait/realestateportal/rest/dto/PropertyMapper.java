package edu.ait.realestateportal.rest.dto;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import edu.ait.realestateportal.usecase.entities.Property;

@Mapper
public interface PropertyMapper {

	public static PropertyMapper INSTANCE = Mappers.getMapper(PropertyMapper.class);

	PropertyDTO fromPropertyToPropertyDTO(Property property);

}
