package edu.ait.realestateportal.usecase;

import java.util.Optional;

import org.springframework.stereotype.Service;

import edu.ait.realestateportal.usecase.entities.Property;
import edu.ait.realestateportal.usecase.persistence.PropertyRepository;

@Service
public class SearchPropertiesService implements SearchPropertyUseCase{

	private PropertyRepository propertyRepository;

	public SearchPropertiesService(PropertyRepository mockRepo) {
		this.propertyRepository = mockRepo;
	}

	@Override
	public Optional<Property> findById(Long id) {
		return propertyRepository.findById(id);
	}

}
