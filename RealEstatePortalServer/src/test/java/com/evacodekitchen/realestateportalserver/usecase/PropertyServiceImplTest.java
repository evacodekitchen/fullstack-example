package com.evacodekitchen.realestateportalserver.usecase;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.evacodekitchen.realestateportalserver.data.PropertyRepository;
import com.evacodekitchen.realestateportalserver.usecase.entity.Property;
import com.evacodekitchen.realestateportalserver.usecase.entity.PropertyType;
import com.evacodekitchen.realestateportalserver.usecase.entity.SaleOrRent;

@RunWith(SpringRunner.class)
public class PropertyServiceImplTest {

	@Autowired
	SearchPropertiesUseCase searchPropertiesService;
	
	@Autowired
	CreatePropertyUseCase createPropertyService;
	
	@MockBean
	PropertyRepository propertyRepository;

	@TestConfiguration
    static class PropertyServiceImplTestContextConfiguration {
  
        @Bean
        public SearchPropertiesUseCase searchPropertiesUseCase() {
            return new SearchPropertiesService();
        }
        
        @Bean
        public CreatePropertyUseCase createPropertyUseCase() {
            return new CreatePropertyService();
        }
        
        @Bean
        public RemovePropertyUseCase removePropertyUseCase() {
            return new RemovePropertyService();
        }
    }
	
	@Test
	public void whenPropertyExistsWithGivenId_ItShouldBeRetrievedFromUseCaseLayer() {
		// given
		Property mockProperty = new Property(123L, PropertyType.HOUSE, SaleOrRent.SALE, 1d, "some desc", "some city", "some street", new byte[0]);
		when(propertyRepository.findById(123L)).thenReturn(Optional.of(mockProperty));
		
		// when
		Optional<Property> propertyFromDbLayer = searchPropertiesService.findPropertyBy(123L);
		
		//then
		assertThat(propertyFromDbLayer.get(), is(mockProperty));
	}

	@Test
	public void whenNoPropertyExistsWithGivenId_EmptyOptionalShouldBeRetrievedFromUseCaseLayer() {
		// given
		Optional<Property> mockNullProperty = Optional.empty();
		when(propertyRepository.findById(123L)).thenReturn(mockNullProperty);
		
		// when
		Optional<Property> propertyFromDbLayer = searchPropertiesService.findPropertyBy(123L);
		
		//then
		assertTrue(propertyFromDbLayer.isEmpty());
	}
	
	@Test
	public void propertyShouldBePassedToDbLayerTBeSaved() {
		// given
		Property propertyToBeAdded = new Property(PropertyType.HOUSE, SaleOrRent.SALE, 1d, "some desc", "some city", "some street", new byte[0]);
		Property mockPropertyWithId = new Property(1L, PropertyType.HOUSE, SaleOrRent.SALE, 1d, "some desc", "some city", "some street", new byte[0]);
		when(propertyRepository.save(propertyToBeAdded)).thenReturn(mockPropertyWithId);
		
		// when
		Property propertySaved = createPropertyService.addNewProperty(propertyToBeAdded);
		
		//then
		assertThat(propertySaved, is(mockPropertyWithId));
	}
	
}
