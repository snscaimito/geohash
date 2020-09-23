package com.testdouble.demo;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(GeoHashAPI.class)
public class GeoHashAPITest {

	// GET /geohash?lat=37.421542&lon=-122.085589

	@Autowired
	private MockMvc mockMvc ;
	
	@MockBean
	private GeoHash geoHash ;
	
	@Test @Disabled
	public void geoHash() throws Exception {
		this.mockMvc.perform(get("/geohash"))
		.andExpect(status().isOk());
	}

	@Test
	public void NoRequestParams() throws Exception {
		this.mockMvc.perform(get("/geohash"))
		.andExpect(status().isBadRequest());
	}

	@Test
	public void fakeReturnValue() throws Exception {
		when(geoHash.calculateNewPosition(Coordinates.parse("37.421542", "-122.085589"), LocalDate.now()))
			.thenReturn(Coordinates.parse("37.421542", "-122.085589")) ;
		
		this.mockMvc.perform(get("/geohash?lat=37.421542&lon=-122.085589"))
		.andExpect(status().isOk())
		.andExpect(content().json("{ \"lat\": 37.421542, \"lon\": -122.085589 }")) 
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andReturn();
	}

}
