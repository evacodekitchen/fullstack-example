package edu.ait.realestateportal.rest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.ait.realestateportal.rest.dto.ErrorDTO;
import edu.ait.realestateportal.rest.dto.PropertyDTO;
import edu.ait.realestateportal.usecase.SearchPropertyUseCase;
import edu.ait.realestateportal.usecase.entities.Property;
import edu.ait.realestateportal.usecase.entities.SaleOrRent;
import edu.ait.realestateportal.usecase.entities.Type;

@RunWith(SpringRunner.class)
@WebMvcTest(PropertyController.class)
public class PropertyControllerTest {

	@Autowired
	MockMvc mockMvc;

	@MockBean
	SearchPropertyUseCase searchPropertyUseCase;
	
	@Autowired
	ObjectMapper objectMapper;

	@Test
	public void propertyByIdEndpoint_ShouldRetrievePropertyReceivedByService() throws Exception {
		// given
		Property fakeProperty = new Property(123L, SaleOrRent.SALE, Type.HOUSE,
				"somecity", "somestreet", null, 3D, "some description");
		when(searchPropertyUseCase.findById(123L)).thenReturn(Optional.of(fakeProperty));

		// when
		MvcResult result = mockMvc.perform(get("/api/v1/properties/123")).andDo(print()).andReturn();

		// then
		String contentAsString = result.getResponse().getContentAsString();
		PropertyDTO retrievedPropertyDTO = objectMapper.readValue(contentAsString, PropertyDTO.class);
		
		assertThat(retrievedPropertyDTO.getId(), is(123L));
		assertThat(retrievedPropertyDTO.getSaleOrRent(), is(fakeProperty.getSaleOrRent()));
		assertThat(retrievedPropertyDTO.getType(), is(fakeProperty.getType()));
		assertThat(retrievedPropertyDTO.getCity(), is(fakeProperty.getCity()));
		assertThat(retrievedPropertyDTO.getStreet(), is(fakeProperty.getStreet()));
		assertThat(retrievedPropertyDTO.getDescription(), is(fakeProperty.getDescription()));
		assertThat(retrievedPropertyDTO.getPicture(), is(fakeProperty.getPicture()));
	}
	
	@Test
	public void propertyByIdEndpoint_ShouldRetrievenErrorDTOWhenPropertyNotFound() throws Exception {
		// given
		when(searchPropertyUseCase.findById(123L)).thenReturn(Optional.empty());

		// when
		MvcResult result = mockMvc.perform(get("/api/v1/properties/123")).andDo(print()).andReturn();

		// then
		String contentAsString = result.getResponse().getContentAsString();
		ErrorDTO errorDTO = objectMapper.readValue(contentAsString, ErrorDTO.class);
		
		assertThat(errorDTO.getStatusCode(), is(404));
		assertThat(errorDTO.getMessage(), is("No value present"));
	}
	


}
