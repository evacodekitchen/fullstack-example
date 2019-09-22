package com.evacodekitchen.realestateportalserver.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evacodekitchen.realestateportalserver.rest.dto.NewPropertyDTO;
import com.evacodekitchen.realestateportalserver.rest.dto.PropertyMapper;
import com.evacodekitchen.realestateportalserver.usecase.PropertyService;
import com.evacodekitchen.realestateportalserver.usecase.entity.Property;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {

	PropertyService propertyService;

	Logger logger = LoggerFactory.getLogger(PropertyController.class);

	public PropertyController(PropertyService propertyService) {
		this.propertyService = propertyService;
	}

	@GetMapping("/{id}")
	public Property getProperty(@PathVariable Long id) {
		return propertyService.findPropertyBy(id).get();
	}

	@PostMapping
	public Property addProperty(@ModelAttribute NewPropertyDTO newPropertyDTO) {
		Property newProperty = PropertyMapper.INSTANCE.newPropertyDTOToProperty(newPropertyDTO);
		logger.info("Property to be added: " + newProperty);
		return propertyService.addNewProperty(newProperty);
	}

}
