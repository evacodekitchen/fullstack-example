package com.evacodekitchen.realestateportalserver.usecase;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.evacodekitchen.realestateportalserver.data.PropertyRepository;
import com.evacodekitchen.realestateportalserver.usecase.entity.Property;

@Service
public class SearchPropertiesService implements SearchPropertiesUseCase {

	@Autowired
	PropertyRepository propertyRepository;

	@Override
	public Optional<Property> findPropertyBy(Long id) {
		return propertyRepository.findById(id);
	}

	@Override
	public Page<Property> getAllProperties(Pageable pageable) {
		return propertyRepository.findAllByOrderByIdAsc(pageable);
	}

	@Override
	public Page<Property> getPropertiesByCity(String city, Pageable pageable) {
		return propertyRepository.findByCityOrderById(city, pageable);
	}
}
