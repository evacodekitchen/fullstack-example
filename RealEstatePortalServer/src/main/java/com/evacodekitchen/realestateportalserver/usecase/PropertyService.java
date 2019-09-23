package com.evacodekitchen.realestateportalserver.usecase;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.evacodekitchen.realestateportalserver.usecase.entity.Property;

public interface PropertyService {
	public Optional<Property> findPropertyBy(Long id);

	public Property addNewProperty(Property newProperty);

	public List<Property> getAllProperties(Pageable pageable);

	public List<Property> getPropertiesByCity(String city, Pageable pageable);
}
