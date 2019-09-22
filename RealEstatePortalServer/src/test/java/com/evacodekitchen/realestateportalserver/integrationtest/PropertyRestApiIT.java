package com.evacodekitchen.realestateportalserver.integrationtest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.FileInputStream;

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
import com.evacodekitchen.realestateportalserver.usecase.entity.SaleOrRent;
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

	@Test
	public void whenPropertyIsSearchById_thenItIsRetrieved() throws Exception {
		// given
		Property mockProperty = new Property(SaleOrRent.RENT, 2d, "some descr", "some city", null, null);
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
		FileInputStream fis = new FileInputStream("src/test/resources/testpicture.jpg");
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
		Property expectedSavedProperty = new Property(rent, price, description, city, street, byteOfPicture);
		assertThat(returnedProperty.getCity(), is(expectedSavedProperty.getCity()));
		assertThat(returnedProperty.getSaleOrRent(), is(expectedSavedProperty.getSaleOrRent()));
		assertThat(returnedProperty.getStreet(), is(expectedSavedProperty.getStreet()));
		assertThat(returnedProperty.getPrice(), is(expectedSavedProperty.getPrice()));
		assertThat(returnedProperty.getDescription(), is(expectedSavedProperty.getDescription()));
		assertThat(returnedProperty.getPicture(), is(expectedSavedProperty.getPicture()));
	}
	
	

}
