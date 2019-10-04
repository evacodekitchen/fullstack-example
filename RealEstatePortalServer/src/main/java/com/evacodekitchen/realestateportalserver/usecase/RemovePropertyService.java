package com.evacodekitchen.realestateportalserver.usecase;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evacodekitchen.realestateportalserver.data.PropertyRepository;

@Service
public class RemovePropertyService implements RemovePropertyUseCase {

	@Autowired
	PropertyRepository propertyRepository;

	@Override
	public void deleteById(Long id) {
		if (propertyRepository.existsById(id))
			propertyRepository.deleteById(id);
		else
			throw new NoSuchElementException("Property with id " + id + " cannot be deleted, because it does not exists");
	}

}
