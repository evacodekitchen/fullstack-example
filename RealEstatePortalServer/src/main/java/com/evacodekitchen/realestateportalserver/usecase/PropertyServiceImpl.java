package com.evacodekitchen.realestateportalserver.usecase;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.evacodekitchen.realestateportalserver.data.PropertyRepository;
import com.evacodekitchen.realestateportalserver.usecase.entity.Property;

@Service
public class PropertyServiceImpl implements PropertyService {

	@Autowired
	PropertyRepository propertyRepository;

	@Override
	public Optional<Property> findPropertyBy(Long id) {
		return propertyRepository.findById(id);
	}

	@Override
	public Property addNewProperty(Property newProperty) {
		return propertyRepository.save(newProperty);
	}

	@Override
	public List<Property> getAllProperties(Pageable pageable) {
		return propertyRepository.findAll(pageable).getContent();
	}

	@Override
	public List<Property> getPropertiesByCity(String city, Pageable pageable) {
//		return propertyRepository.findAll().stream().filter(property -> property.getCity().equals(city)).skip(pageable.getOffset()).limit(pageable.getPageSize())
//				.collect(Collectors.toList());
		return propertyRepository.findByCity(city, pageable);
	}

	@Override
	public void deleteById(Long id) {
		if (propertyRepository.existsById(id))
			propertyRepository.deleteById(id);
		else
			throw new NoSuchElementException("Property with id " + id + " cannot be deleted, because it does not exists");
	}

}
