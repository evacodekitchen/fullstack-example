package com.evacodekitchen.realestateportalserver.data;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.evacodekitchen.realestateportalserver.usecase.entity.Property;
import com.evacodekitchen.realestateportalserver.usecase.entity.PropertyType;
import com.evacodekitchen.realestateportalserver.usecase.entity.SaleOrRent;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PropertyRepositoryTest {

	@Autowired
	private TestEntityManager testEntityManager;

	@Autowired
	private PropertyRepository propertyRepository;

	@Test
	public void propertyByIdShouldBeRetrived() {
		// given
		Property mockProperty = new Property(PropertyType.FLAT, SaleOrRent.RENT, 456d, "some description", "some city", "some street",
				new byte[0]);
		Property savedProperty = testEntityManager.persist(mockProperty);

		// when
		Optional<Property> retrievedProperty = propertyRepository.findById(mockProperty.getId());

		// then
		assertThat(retrievedProperty.get(), is(savedProperty));
	}

	@Test
	public void propertyShouldBePersisted() {
		// given
		Property propertyToBePersisted = new Property(PropertyType.FLAT, SaleOrRent.RENT, 456d, "some description", "some city", "some street",
				new byte[0]);
		
		// when
		Property savedProperty = propertyRepository.save(propertyToBePersisted);
		
		// then
		assertThat(savedProperty, is(propertyToBePersisted));
	}

}
