package com.evacodekitchen.realestateportalserver.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.io.FileInputStream;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.evacodekitchen.realestateportalserver.rest.dto.ErrorDTO;
import com.evacodekitchen.realestateportalserver.usecase.PropertyService;
import com.evacodekitchen.realestateportalserver.usecase.entity.Property;
import com.evacodekitchen.realestateportalserver.usecase.entity.SaleOrRent;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(PropertyController.class)
public class PropertyControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	PropertyService propertyService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void whenPropertyIsSearchById_thenItIsRetrieved() throws Exception {
		// given
		Property mockProperty = new Property(12345L, SaleOrRent.RENT, 2d, "some descr", "some city", null, null);
		when(propertyService.findPropertyBy(12345L)).thenReturn(Optional.of(mockProperty));

		// when
		MvcResult result = mockMvc.perform(get("/api/v1/properties/12345")).andReturn();

		// then
		assertThat(result.getResponse().getStatus(), is(200));
		String contentAsString = result.getResponse().getContentAsString();
		Property propertyInResponse = objectMapper.readValue(contentAsString, Property.class);
		assertThat(propertyInResponse, is(mockProperty));
	}

	@Test
	public void whenPropertySearchByIdNotExists_thenNotFoundShouldBeRetrieved() throws Exception {
		// given
		when(propertyService.findPropertyBy(12345L)).thenReturn(Optional.empty());

		// when
		MvcResult result = mockMvc.perform(get("/api/v1/properties/12345")).andReturn();

		// then
		assertThat(result.getResponse().getStatus(), is(404));
		String contentAsString = result.getResponse().getContentAsString();
		ErrorDTO errorInResponse = objectMapper.readValue(contentAsString, ErrorDTO.class);
		assertThat(errorInResponse.getMessage(), is("No value present"));
		assertThat(errorInResponse.getCode(), is(404));
	}

	@Test
	public void whenNewPropertyIsPosted_thenTheSavedPropertyIsRetrieved() throws Exception {
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
		Property mockPropertyForService = new Property(rent, price, description, city, street,
				byteOfPicture);
		
		Property mockPropertyFromService = new Property(1L, rent, price, description, city,
				street, byteOfPicture);
		when(propertyService.addNewProperty(mockPropertyForService)).thenReturn(mockPropertyFromService);

		// when
        MockHttpServletResponse result = mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/properties")
                        .file(picture)
                        .param("city", city)
                        .param("description", description)
                        .param("saleOrRent", rent.toString())
                        .param("price", price.toString())
                        .param("street", street)).andReturn().getResponse();
        
        //then
		assertThat(result.getStatus(), is(200));
		Property returnedProperty = objectMapper.readValue(result.getContentAsString(), Property.class);
		assertThat(returnedProperty,is(mockPropertyFromService));
	}

}
