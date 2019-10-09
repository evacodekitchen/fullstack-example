package edu.ait.realestateportal.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import edu.ait.realestateportal.usecase.entities.Property;
import edu.ait.realestateportal.usecase.entities.SaleOrRent;
import edu.ait.realestateportal.usecase.entities.Type;
import edu.ait.realestateportal.usecase.persistence.PropertyRepository;

@RunWith(SpringRunner.class)
public class SearchPropertiesServiceTest {

	@MockBean
	PropertyRepository mockRepo;

	@Test
	public void findByIdShouldReturnPropertyFromRepo() {
		//given
		SearchPropertyUseCase searchPropertyUseCase = new SearchPropertiesService(mockRepo);
		Property fakeProperty = new Property(123L, SaleOrRent.SALE, Type.HOUSE,
				"somecity", "somestreet", null, 3D, "some description");
		when(mockRepo.findById(123L)).thenReturn(Optional.of(fakeProperty));
		
		//when
		Optional<Property> property = searchPropertyUseCase.findById(123L);
		
		//then
		assertThat(property.get().getId(), is(fakeProperty.getId()));
		assertThat(property.get().getCity(), is(fakeProperty.getCity()));
		assertThat(property.get().getDescription(), is(fakeProperty.getDescription()));
		
	}

}
