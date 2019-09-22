package com.evacodekitchen.realestateportalserver.usecase;

import java.util.Optional;

import com.evacodekitchen.realestateportalserver.usecase.entity.Property;

public interface PropertyService {
	public Optional<Property> findPropertyBy(Long id);

	public Property addNewProperty(Property newProperty);
}
