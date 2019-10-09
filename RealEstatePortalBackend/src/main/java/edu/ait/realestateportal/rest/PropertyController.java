package edu.ait.realestateportal.rest;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ait.realestateportal.rest.dto.ErrorDTO;
import edu.ait.realestateportal.rest.dto.PropertyDTO;
import edu.ait.realestateportal.rest.dto.PropertyMapper;
import edu.ait.realestateportal.usecase.SearchPropertyUseCase;
import edu.ait.realestateportal.usecase.entities.Property;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {
	
	SearchPropertyUseCase searchPropertyUseCase;
	
	public PropertyController(SearchPropertyUseCase searchPropertyUseCase) {
		this.searchPropertyUseCase = searchPropertyUseCase;
	}

	@GetMapping("/{id}")
	public PropertyDTO findById(@PathVariable Long id) {
		Optional<Property> property = searchPropertyUseCase.findById(id);
		PropertyDTO propertyDTO = PropertyMapper.INSTANCE.fromPropertyToPropertyDTO(property.get());
		return propertyDTO;
	}

}
