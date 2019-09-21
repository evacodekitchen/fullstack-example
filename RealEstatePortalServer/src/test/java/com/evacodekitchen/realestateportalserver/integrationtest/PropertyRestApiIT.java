package com.evacodekitchen.realestateportalserver.integrationtest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
		MockHttpServletResponse response = mockMvc.perform(get("/api/v1/properties/" + savedProperty.getId())).andReturn()
				.getResponse();

		// then
		assertThat(response.getStatus(), is(HttpStatus.OK.value()));
		assertThat(response.getContentType(), is(MediaType.APPLICATION_JSON_UTF8.toString()));
		String contentAsString = response.getContentAsString();
		Property propertyInResponse = objectMapper.readValue(contentAsString, Property.class);
		assertThat(propertyInResponse, is(savedProperty));
	}

}
