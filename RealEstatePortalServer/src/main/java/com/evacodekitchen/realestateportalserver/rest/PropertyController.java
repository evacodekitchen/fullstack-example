package com.evacodekitchen.realestateportalserver.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.evacodekitchen.realestateportalserver.usecase.PropertyService;
import com.evacodekitchen.realestateportalserver.usecase.entity.Property;

@RestController
@RequestMapping("/api/v1/properties")
public class PropertyController {
	
	PropertyService propertyService;

	public PropertyController(PropertyService propertyService) {
		this.propertyService = propertyService;
	}

	
	@GetMapping("/{id}")
	public Property getProperty(@PathVariable Long id) {
		return propertyService.findPropertyBy(id).get();
	}

}
