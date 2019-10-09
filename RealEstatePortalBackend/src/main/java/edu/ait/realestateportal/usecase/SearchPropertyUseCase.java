package edu.ait.realestateportal.usecase;

import java.util.Optional;

import edu.ait.realestateportal.usecase.entities.Property;

public interface SearchPropertyUseCase {

	Optional<Property> findById(Long id);

}
