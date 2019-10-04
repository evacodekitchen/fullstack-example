package com.evacodekitchen.realestateportalserver.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evacodekitchen.realestateportalserver.data.PropertyRepository;
import com.evacodekitchen.realestateportalserver.usecase.entity.Property;

@Service
public class CreatePropertyService implements CreatePropertyUseCase {

	@Autowired
	PropertyRepository propertyRepository;

	@Override
	public Property addNewProperty(Property newProperty) {
		return propertyRepository.save(newProperty);
	}

}
