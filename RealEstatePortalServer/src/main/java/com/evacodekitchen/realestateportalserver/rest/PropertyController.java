package com.evacodekitchen.realestateportalserver.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.evacodekitchen.realestateportalserver.rest.dto.NewPropertyDTO;
import com.evacodekitchen.realestateportalserver.rest.dto.PropertyDTO;
import com.evacodekitchen.realestateportalserver.rest.dto.PropertyMapper;
import com.evacodekitchen.realestateportalserver.rest.dto.PropertyPageDTO;
import com.evacodekitchen.realestateportalserver.usecase.CreatePropertyUseCase;
import com.evacodekitchen.realestateportalserver.usecase.RemovePropertyUseCase;
import com.evacodekitchen.realestateportalserver.usecase.SearchPropertiesUseCase;
import com.evacodekitchen.realestateportalserver.usecase.entity.Property;


@RestController
@RequestMapping("/api/v1/properties")
@CrossOrigin(origins = "http://localhost:3000")
public class PropertyController {

	@Autowired
	SearchPropertiesUseCase searhPropertiesService;
	
	@Autowired
	CreatePropertyUseCase createPropertyService;
	
	@Autowired
	RemovePropertyUseCase removePropertyService;

	Logger logger = LoggerFactory.getLogger(PropertyController.class);

	@GetMapping("/{id}")
	public PropertyDTO getProperty(@PathVariable Long id) {
		return PropertyMapper.INSTANCE.propertyToPropertyDTO(searhPropertiesService.findPropertyBy(id).get());
	}

	@PostMapping
	public PropertyDTO addProperty(@ModelAttribute NewPropertyDTO newPropertyDTO) {
		Property newProperty = PropertyMapper.INSTANCE.newPropertyDTOToProperty(newPropertyDTO);
		logger.info("Property to be added: " + newProperty);
		return PropertyMapper.INSTANCE.propertyToPropertyDTO(createPropertyService.addNewProperty(newProperty));
	}

	@GetMapping
	public PropertyPageDTO getAllProperties(@RequestParam(required = false) String city, Pageable pageable) {
		Page<Property> page = null;
		if (city == null) {
			page = searhPropertiesService.getAllProperties(pageable);
		} else {
			page = searhPropertiesService.getPropertiesByCity(city, pageable);
		}
		logger.info("Nr of properties to be listed " + page.getNumberOfElements());
		return new PropertyPageDTO(page.getContent(), page.getTotalPages());
	}

	@DeleteMapping("/{id}")
	public void deleteProperty(@PathVariable Long id) {
		removePropertyService.deleteById(id);
	}

}
