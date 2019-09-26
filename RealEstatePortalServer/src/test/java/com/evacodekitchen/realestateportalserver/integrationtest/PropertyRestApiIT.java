package com.evacodekitchen.realestateportalserver.integrationtest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.FileInputStream;
import java.util.List;
import java.util.Optional;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.evacodekitchen.realestateportalserver.data.PropertyRepository;
import com.evacodekitchen.realestateportalserver.usecase.entity.Property;
import com.evacodekitchen.realestateportalserver.usecase.entity.PropertyType;
import com.evacodekitchen.realestateportalserver.usecase.entity.SaleOrRent;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application.properties")
public class PropertyRestApiIT {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	private PropertyRepository propertyRepository;

	@Autowired
	private ObjectMapper objectMapper;

	@Before
	public void beforeTestMethods() {
		propertyRepository.deleteAll();
	}

	@Test
	public void whenPropertyIsSearchById_thenItIsRetrieved() throws Exception {
		// given
		Property mockProperty = new Property(PropertyType.HOUSE, SaleOrRent.RENT, 2d, "some descr", "some city", null, null);
		Property savedProperty = propertyRepository.save(mockProperty);

		// when
		MockHttpServletResponse response = mockMvc.perform(get("/api/v1/properties/" + savedProperty.getId()))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus(), is(HttpStatus.OK.value()));
		assertThat(response.getContentType(), is(MediaType.APPLICATION_JSON_UTF8.toString()));
		String contentAsString = response.getContentAsString();
		Property propertyInResponse = objectMapper.readValue(contentAsString, Property.class);
		assertThat(propertyInResponse, is(savedProperty));
	}

	@Test
	public void whenNewPropertyIsPosted_thenTheSavedPropertyIsRetrievedFromInMemoryDb() throws Exception {
		// given
		FileInputStream fis = new FileInputStream("src/test/resources/house1.jpg");
		byte[] byteOfPicture = fis.readAllBytes();
		MockMultipartFile picture = new MockMultipartFile("picture", "somePicture.jpg", "text/plain", byteOfPicture);
		fis.close();

		String description = "some descr";
		String city = "some city";
		String street = "some street";
		SaleOrRent rent = SaleOrRent.RENT;
		Double price = 2d;

		// when
		MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/properties")
				.file(picture).param("city", city).param("description", description)
				.param("saleOrRent", rent.toString()).param("price", price.toString()).param("street", street))
				.andReturn().getResponse();

		// then
		assertThat(result.getStatus(), is(200));
		Property returnedProperty = objectMapper.readValue(result.getContentAsString(), Property.class);
		Property expectedSavedProperty = new Property(PropertyType.FLAT, rent, price, description, city, street, byteOfPicture);
		assertThat(returnedProperty.getCity(), is(expectedSavedProperty.getCity()));
		assertThat(returnedProperty.getSaleOrRent(), is(expectedSavedProperty.getSaleOrRent()));
		assertThat(returnedProperty.getStreet(), is(expectedSavedProperty.getStreet()));
		assertThat(returnedProperty.getPrice(), is(expectedSavedProperty.getPrice()));
		assertThat(returnedProperty.getDescription(), is(expectedSavedProperty.getDescription()));
		assertThat(returnedProperty.getPicture(), is(expectedSavedProperty.getPicture()));
	}

	@Test
	public void whenSearchForAllProperties_thenAllPropertiesAreRetrieved() throws Exception {
		// given
		Property mockProperty1 = new Property(PropertyType.HOUSE, SaleOrRent.RENT, 2d, "some descr1", "some city1", null, null);
		Property mockProperty2 = new Property(PropertyType.HOUSE, SaleOrRent.RENT, 3d, "some descr2", "some city2", null, null);
		Property mockProperty3 = new Property(PropertyType.HOUSE, SaleOrRent.RENT, 4d, "some descr3", "some city3", null, null);
		Property savedProperty1 = propertyRepository.save(mockProperty1);
		Property savedProperty2 = propertyRepository.save(mockProperty2);
		Property savedProperty3 = propertyRepository.save(mockProperty3);

		// when
		MockHttpServletResponse response = mockMvc.perform(get("/api/v1/properties")).andReturn().getResponse();

		// then
		assertThat(response.getStatus(), is(HttpStatus.OK.value()));
		assertThat(response.getContentType(), is(MediaType.APPLICATION_JSON_UTF8.toString()));
		String contentAsString = response.getContentAsString();
		List<Property> propertiesInResponse = (List<Property>) objectMapper.readValue(contentAsString,
				new TypeReference<List<Property>>() {
				});
		assertThat(propertiesInResponse.size(), is(3));
		assertThat(propertiesInResponse.get(0), is(savedProperty1));
		assertThat(propertiesInResponse.get(1), is(savedProperty2));
		assertThat(propertiesInResponse.get(2), is(savedProperty3));
	}

	@Test
	public void whenSearchForAllProperties_thenFirstPageIsRetrieved() throws Exception {
		// given
		Property mockProperty1 = new Property(PropertyType.HOUSE, SaleOrRent.RENT, 2d, "some descr1", "some city1", null, null);
		Property mockProperty2 = new Property(PropertyType.HOUSE, SaleOrRent.RENT, 3d, "some descr2", "some city2", null, null);
		Property mockProperty3 = new Property(PropertyType.HOUSE, SaleOrRent.RENT, 4d, "some descr3", "some city3", null, null);
		Property savedProperty1 = propertyRepository.save(mockProperty1);
		Property savedProperty2 = propertyRepository.save(mockProperty2);
		Property savedProperty3 = propertyRepository.save(mockProperty3);

		// when
		MockHttpServletResponse response = mockMvc.perform(get("/api/v1/properties?page=0&size=2")).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus(), is(HttpStatus.OK.value()));
		assertThat(response.getContentType(), is(MediaType.APPLICATION_JSON_UTF8.toString()));
		String contentAsString = response.getContentAsString();
		List<Property> propertiesInResponse = (List<Property>) objectMapper.readValue(contentAsString,
				new TypeReference<List<Property>>() {
				});
		assertThat(propertiesInResponse.size(), is(2));
		assertThat(propertiesInResponse.get(0), is(savedProperty1));
		assertThat(propertiesInResponse.get(1), is(savedProperty2));
	}

	@Test
	public void whenSearchForPropertiesInGivenCity_thenOnlyPropertiesInThatCityAreRetrieved() throws Exception {
		// given
		Property mockProperty1 = new Property(PropertyType.HOUSE, SaleOrRent.RENT, 2d, "some descr1", "some city1", null, null);
		Property mockProperty2 = new Property(PropertyType.HOUSE, SaleOrRent.RENT, 3d, "some descr2", "some city2", null, null);
		Property mockProperty3 = new Property(PropertyType.FLAT, SaleOrRent.RENT, 4d, "some descr3", "some city1", null, null);
		Property savedProperty1 = propertyRepository.save(mockProperty1);
		Property savedProperty2 = propertyRepository.save(mockProperty2);
		Property savedProperty3 = propertyRepository.save(mockProperty3);

		// when
		MockHttpServletResponse response = mockMvc.perform(get("/api/v1/properties?city=some city1")).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus(), is(HttpStatus.OK.value()));
		assertThat(response.getContentType(), is(MediaType.APPLICATION_JSON_UTF8.toString()));
		String contentAsString = response.getContentAsString();
		List<Property> propertiesInResponse = (List<Property>) objectMapper.readValue(contentAsString,
				new TypeReference<List<Property>>() {
				});
		assertThat(propertiesInResponse.size(), is(2));
		assertThat(propertiesInResponse.get(0), is(savedProperty1));
		assertThat(propertiesInResponse.get(1), is(savedProperty3));
	}

	@Test
	public void whenDeleteApiIsCalled_thenPropertyIsDeletedFromRepo() throws Exception {
		// given
		Property mockProperty = new Property(PropertyType.FLAT, SaleOrRent.RENT, 2d, "some descr", "some city", null, null);
		Property savedProperty = propertyRepository.save(mockProperty);

		// when
		MockHttpServletResponse response = mockMvc.perform(delete("/api/v1/properties/" + savedProperty.getId()))
				.andReturn().getResponse();

		// then
		assertThat(response.getStatus(), is(HttpStatus.OK.value()));
		assertThat(response.getContentAsString(), Matchers.isEmptyString());

		Optional<Property> notFoundProperty = propertyRepository.findById(savedProperty.getId());
		assertTrue(notFoundProperty.isEmpty());
	}

	@Test
	public void whenDeleteApiIsCalledForUnexistentProperty_thenNotFoundIsReturned() throws Exception {
		// when
		MockHttpServletResponse response = mockMvc.perform(delete("/api/v1/properties/1234")).andReturn().getResponse();

		// then
		assertThat(response.getStatus(), is(HttpStatus.NOT_FOUND.value()));
		assertThat(response.getContentAsString(), containsString("not exists"));
	}

}
