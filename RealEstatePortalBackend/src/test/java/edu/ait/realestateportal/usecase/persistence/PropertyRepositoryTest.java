package edu.ait.realestateportal.usecase.persistence;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import edu.ait.realestateportal.usecase.entities.Property;
import edu.ait.realestateportal.usecase.entities.SaleOrRent;
import edu.ait.realestateportal.usecase.entities.Type;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PropertyRepositoryTest {

	@Autowired
	TestEntityManager testEntityManager;

	@Autowired
	PropertyRepository propertyRepository;

	@Test
	public void findById_ShouldReturnProperty() {
		// given
		Property fakeProperty = new Property(SaleOrRent.SALE, Type.HOUSE, "somecity", "somestreet", null, 3D,
				"some description");
		Property savedProperty = testEntityManager.persist(fakeProperty);

		// when
		Optional<Property> propertyFromDb = propertyRepository.findById(savedProperty.getId());

		// then
		assertThat(propertyFromDb.get(), is(savedProperty));
		
	}

}
