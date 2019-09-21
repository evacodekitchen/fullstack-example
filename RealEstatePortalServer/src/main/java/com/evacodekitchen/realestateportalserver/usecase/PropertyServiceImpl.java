package com.evacodekitchen.realestateportalserver.usecase;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evacodekitchen.realestateportalserver.data.PropertyRepository;
import com.evacodekitchen.realestateportalserver.usecase.entity.Property;

@Service
public class PropertyServiceImpl implements PropertyService{

	@Autowired
	PropertyRepository propertyRepository;
	
	@Override
	public Optional<Property> findPropertyBy(Long id) {
		return propertyRepository.findById(id);
	}

}
