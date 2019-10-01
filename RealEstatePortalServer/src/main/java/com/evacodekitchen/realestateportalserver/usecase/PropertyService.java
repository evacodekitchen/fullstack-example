package com.evacodekitchen.realestateportalserver.usecase;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.evacodekitchen.realestateportalserver.usecase.entity.Property;

public interface PropertyService {
	public Optional<Property> findPropertyBy(Long id);

	public Property addNewProperty(Property newProperty);

	public Page<Property> getAllProperties(Pageable pageable);

	public Page<Property> getPropertiesByCity(String city, Pageable pageable);

	public void deleteById(Long id);
}
